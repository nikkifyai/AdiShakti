package com.ex.safe.adishakti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val ivMenu = findViewById<ImageView>(R.id.ivMenu)

ivMenu.setOnClickListener {
    startActivity(Intent(this, SettingsActivity::class.java))
}
    }
}