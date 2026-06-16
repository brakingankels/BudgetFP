package com.example.myapplicationbudgetplease.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationbudgetplease.R
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import kotlinx.coroutines.launch

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
        val db =
            BudgetDatabase.getDatabase(this)

        btnLogin.setOnClickListener {

            val username =
                edtUsername.text.toString().trim()

            val password =
                edtPassword.text.toString().trim()

            lifecycleScope.launch {

                val user =
                    db.userDao().login(
                        username,
                        password
                    )

                if (user != null) {

                    startActivity(
                        Intent(
                            this@LoginActivity,
                            DashboardActivity::class.java
                        )
                    )

                    finish()

                } else {

                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid Username or Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Navigate to Registration Screen from main button
        btnRegisterMain.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}