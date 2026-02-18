package com.ex.safe.adishakti.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.ex.safe.adishakti.EvidenceActivity
import com.ex.safe.adishakti.R
import com.ex.safe.adishakti.util.PermissionHelper
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraXRecordingService : LifecycleService() {

    private lateinit var cameraExecutor: ExecutorService
    private var recording: Recording? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recordedFile: File? = null
    private var isRecordingActive = true

    override fun onCreate() {
        super.onCreate()

        cameraExecutor = Executors.newSingleThreadExecutor()

        startForegroundNotification()
        initializeCamera()
    }

    private fun startForegroundNotification() {

        val channelId = "adishakti_recording"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Emergency Recording",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setSound(null, null)
                enableVibration(false)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification: Notification =
            NotificationCompat.Builder(this, channelId)
                .setContentTitle("Protection Active")
                .setContentText("Recording evidence")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSilent(true)
                .build()

        startForeground(1, notification)
    }

    private fun initializeCamera() {

        if (!PermissionHelper.hasCameraPermission(this)) {
            stopSelf()
            return
        }

        val providerFuture = ProcessCameraProvider.getInstance(this)

        providerFuture.addListener({

            val cameraProvider = providerFuture.get()

            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HD))
                .build()

            videoCapture = VideoCapture.withOutput(recorder)

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, // LifecycleService is LifecycleOwner
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    videoCapture
                )

                startNewSegment()

            } catch (e: Exception) {
                e.printStackTrace()
                stopSelf()
            }

        }, cameraExecutor)
    }

    private fun startNewSegment() {

        if (!isRecordingActive) return

        if (!PermissionHelper.hasCameraPermission(this)) {
            stopSelf()
            return
        }

        val dir = getExternalFilesDir(null) ?: return

        recordedFile = File(
            dir,
            "evidence_${System.currentTimeMillis()}.mp4"
        )

        val outputOptions =
            FileOutputOptions.Builder(recordedFile!!).build()

        try {

            val pendingRecording =
                videoCapture?.output?.prepareRecording(this, outputOptions)
                    ?: return

            recording =
                if (PermissionHelper.hasAudioPermission(this)) {

                    try {
                        pendingRecording
                            .withAudioEnabled()
                            .start(ContextCompat.getMainExecutor(this)) { event ->
                                handleEvent(event)
                            }
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                        null
                    }

                } else {

                    pendingRecording
                        .start(ContextCompat.getMainExecutor(this)) { event ->
                            handleEvent(event)
                        }
                }

        } catch (e: SecurityException) {
            e.printStackTrace()
            stopSelf()
        }
    }

    private fun handleEvent(event: VideoRecordEvent) {

        when (event) {

            is VideoRecordEvent.Start -> {

                Handler(Looper.getMainLooper()).postDelayed({
                    recording?.stop()
                }, 120_000)
            }

            is VideoRecordEvent.Finalize -> {

                recordedFile?.let { file ->
                    uploadToSupabase(file)
                }

                startNewSegment()
            }
        }
    }

    private fun uploadToSupabase(file: File) {

        lifecycleScope.launch {

            try {

                Supabase.client.storage
                    .from("evidence")
                    .upload(file.name, file)

                openEvidenceScreen()

            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun openEvidenceScreen() {

        val intent = Intent(this, EvidenceActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        isRecordingActive = false
        recording?.stop()
        cameraExecutor.shutdown()
    }
}
