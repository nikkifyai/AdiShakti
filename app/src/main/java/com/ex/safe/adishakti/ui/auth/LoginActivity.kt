package com.ex.safe.adishakti.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ex.safe.adishakti.HomeActivity
import com.ex.safe.adishakti.R

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        val receivedEmail = intent.getStringExtra("email")
        val receivedPassword = intent.getStringExtra("password")

        etEmail.setText(receivedEmail ?: "")
        etPassword.setText(receivedPassword ?: "")

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Valid email required"
                return@setOnClickListener
            }

            if (password.length < 6) {
                etPassword.error = "Minimum 6 characters required"
                return@setOnClickListener
            }

            // ✅ SAVE LOGIN STATE
            val prefs = getSharedPreferences("login_prefs", MODE_PRIVATE)
            prefs.edit().putBoolean("isLoggedIn", true).apply()

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
