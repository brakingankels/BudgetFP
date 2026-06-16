package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import com.example.myapplicationbudgetplease.database.entities.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtUsername =
            findViewById<EditText>(R.id.edtRegUsername)

        val edtPassword =
            findViewById<EditText>(R.id.edtRegPassword)

        val btnRegister =
            findViewById<Button>(R.id.btnRegister)

        val db =
            BudgetDatabase.getDatabase(this)

        btnRegister.setOnClickListener {

            val username =
                edtUsername.text.toString().trim()

            val password =
                edtPassword.text.toString().trim()

            if (
                username.isEmpty() ||
                password.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.userDao().insert(
                    User(
                        username = username,
                        password = password
                    )
                )

                Toast.makeText(
                    this@RegisterActivity,
                    "User Registered!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}