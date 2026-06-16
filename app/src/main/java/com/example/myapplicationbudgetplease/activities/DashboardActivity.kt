

package com.example.myapplicationbudgetplease.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationbudgetplease.R

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnCategories =
            findViewById<Button>(R.id.btnCategories)

        btnCategories.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CategoryActivity::class.java
                )
            )
        }
        val btnExpenses = findViewById<Button>(R.id.btnExpenses)

        btnExpenses.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ExpenseActivity::class.java
                )
            )
            startActivity(
                Intent(
                    this,
                    GoalActivity::class.java
                )
            )
        }
    }
}
