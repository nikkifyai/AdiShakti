package com.ex.safe.adishakti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ex.safe.adishakti.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            val prefs = getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
            val isFirstTime = prefs.getBoolean("isFirstTime", true)

            if (isFirstTime) {
                startActivity(Intent(this, OnboardingActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }

            finish()

        }, 2000)
    }
}