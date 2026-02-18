package com.ex.safe.adishakti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.ex.safe.adishakti.ui.auth.LoginActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val ivMenu = findViewById<ImageView>(R.id.ivMenu)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        ivMenu.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        btnLogout.setOnClickListener {

            val prefs = getSharedPreferences("login_prefs", MODE_PRIVATE)
            prefs.edit().putBoolean("isLoggedIn", false).apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
