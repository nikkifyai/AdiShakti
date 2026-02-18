package com.ex.safe.adishakti

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ex.safe.adishakti.util.EmergencyStorage

class EmergencyContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact)

        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            val phone = etPhone.text.toString().trim()

            if (phone.length < 10) {
                Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            EmergencyStorage.savePhone(this, phone)
            Toast.makeText(this, "Emergency contact saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
