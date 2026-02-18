package com.ex.safe.adishakti

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val ivClose = findViewById<ImageView>(R.id.ivClose)
        val cardEmergency =
            findViewById<LinearLayout>(R.id.cardEmergencyContact)

        ivClose.setOnClickListener {
            finish()
        }

        cardEmergency.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    EmergencyContactActivity::class.java
                )
            )
        }
    }
}
