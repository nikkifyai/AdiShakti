package com.ex.safe.adishakti

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ex.safe.adishakti.databinding.ActivityEmergencyDashboardBinding

class EmergencyDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trustedPeopleButton.setOnClickListener {
            startActivity(Intent(this, TrustedContactsActivity::class.java))
        }

        binding.emergencyContactsButton.setOnClickListener {
            startActivity(Intent(this, EmergencyContactActivity::class.java))
        }

        binding.familyMembersButton.setOnClickListener {
            // Create and open FamilyMembersActivity
        }

        binding.recentEvidenceButton.setOnClickListener {
            startActivity(Intent(this, EvidenceActivity::class.java))
        }
    }
}
