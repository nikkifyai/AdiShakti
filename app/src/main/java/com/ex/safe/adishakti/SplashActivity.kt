package com.ex.safe.adishakti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ex.safe.adishakti.ui.auth.LoginActivity
import com.ex.safe.adishakti.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            val onboardingPrefs = getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
            val isFirstTime = onboardingPrefs.getBoolean("isFirstTime", true)

            val loginPrefs = getSharedPreferences("login_prefs", MODE_PRIVATE)
            val isLoggedIn = loginPrefs.getBoolean("isLoggedIn", false)

            when {
                isFirstTime -> {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }

                isLoggedIn -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                }

                else -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }

            finish()

        }, 2000)
    }
}
