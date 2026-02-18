package com.ex.safe.adishakti

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ex.safe.adishakti.databinding.ActivityHomeBinding
import com.ex.safe.adishakti.service.BubbleService

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emergencyContactButton.setOnClickListener {
            startActivity(Intent(this, EmergencyContactActivity::class.java))
        }

        binding.trustedContactButton.setOnClickListener {
            startActivity(Intent(this, TrustedContactsActivity::class.java))
        }

        binding.recordingsButton.setOnClickListener {
            startActivity(Intent(this, RecordingsActivity::class.java))
        }

        binding.activateBubbleButton.setOnClickListener {
            startService(Intent(this, BubbleService::class.java))
        }
    }
}
