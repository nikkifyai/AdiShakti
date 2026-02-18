package com.ex.safe.adishakti.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ex.safe.adishakti.util.SupabaseHelper
import java.io.File

class UploadService : Service() {

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        val filePath = intent?.getStringExtra("file_path")

        if (filePath != null) {

            val file = File(filePath)

            SupabaseHelper.uploadFile(file) { url ->

                // Upload finished
                stopSelf()
            }
        } else {
            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
