package com.ex.safe.adishakti.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import androidx.core.content.ContextCompat
import com.ex.safe.adishakti.service.CameraXRecordingService
import com.ex.safe.adishakti.util.EmergencyHelper
import com.ex.safe.adishakti.util.EmergencyStorage

class VolumeAccessibilityService : AccessibilityService() {

    private var lastPressTime = 0L
    private var pressCount = 0
    private var lastTriggerTime = 0L

    override fun onKeyEvent(event: KeyEvent): Boolean {

        Log.d("VOL_ACCESS", "onKeyEvent: $event")

        if (event.action == KeyEvent.ACTION_DOWN &&
            event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

            Log.d("VOL_ACCESS", "Volume Down Pressed")

            val currentTime = System.currentTimeMillis()

            // Count presses within 1.2 seconds
            pressCount = if (currentTime - lastPressTime < 1200) {
                pressCount + 1
            } else {
                1
            }

            lastPressTime = currentTime

            Log.d("VOL_ACCESS", "Press Count: $pressCount")

            // Trigger on 5 presses
            if (pressCount >= 5) {

                // Prevent multiple triggers within 10 seconds
                if (currentTime - lastTriggerTime > 10000) {
                    lastTriggerTime = currentTime
                    Log.d("VOL_ACCESS", "5x Trigger Activated")
                    startRecordingService()
                } else {
                    Log.d("VOL_ACCESS", "Trigger on cooldown")
                }

                pressCount = 0
            }

            return true
        }

        return super.onKeyEvent(event)
    }

    private fun startRecordingService() {

        Log.d("REC_SERVICE", "Attempting to start Recording Service")

        // Send Emergency SMS
        val phone = EmergencyStorage.getPhone(this)
        if (!phone.isNullOrEmpty()) {
            Log.d("REC_SERVICE", "Sending emergency SMS to $phone")
            EmergencyHelper.sendEmergencySMS(this, phone)
        } else {
            Log.d("REC_SERVICE", "No emergency contact found for SMS")
        }

        val intent = Intent(this, CameraXRecordingService::class.java)

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, intent)
                Log.d("REC_SERVICE", "Started foreground service")
            } else {
                startService(intent)
                Log.d("REC_SERVICE", "Started service")
            }
        } catch (e: Exception) {
            Log.e("REC_SERVICE", "Error starting service: ${e.message}")
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Not required
    }

    override fun onInterrupt() {
        Log.d("VOL_ACCESS", "Accessibility service interrupted")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("VOL_ACCESS", "Accessibility service connected")
    }
}
