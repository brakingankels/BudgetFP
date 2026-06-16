package com.example.myapplicationbudgetplease.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationbudgetplease.R

class LoginActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegisterMain: Button // Added for side-by-side register button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegisterMain = findViewById(R.id.btnRegisterMain)

        // Existing Hardcoded Login Logic
        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            // This is where your hardcoded check lives
            if (username == "admin" && password == "admin123") {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish() // Optional: Closes LoginActivity so clicking 'back' doesn't return here
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to Registration Screen from main button
        btnRegisterMain.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}