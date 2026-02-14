package com.ex.safe.adishakti.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ex.safe.adishakti.HomeActivity
import com.ex.safe.adishakti.R

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val tvSkip = findViewById<TextView>(R.id.tvSkip)

        val onboardingItems = listOf(

            OnboardingItem(
                R.drawable.illustrationbg,
                "Real-Time Location",
                "Share your live location with trusted people instantly."
            ),

            OnboardingItem(
                R.drawable.illustrationbg,
                "Emergency SOS",
                "Send instant alerts with your location to your family."
            ),

            OnboardingItem(
                R.drawable.illustrationbg,
                "Stay Protected",
                "Create your safety bubble and feel secure everywhere."
            )
        )

        viewPager.adapter = OnboardingAdapter(onboardingItems)

        btnNext.setOnClickListener {

            if (viewPager.currentItem < onboardingItems.size - 1) {
                viewPager.currentItem += 1
            } else {

                val prefs = getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
                prefs.edit().putBoolean("isFirstTime", false).apply()

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        tvSkip.setOnClickListener {
            val prefs = getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
            prefs.edit().putBoolean("isFirstTime", false).apply()

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}