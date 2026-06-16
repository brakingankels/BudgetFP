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

        val btnExpenses =
            findViewById<Button>(R.id.btnExpenses)

        val btnGoals = 
            findViewById<Button>(R.id.btnGoals)

        val btnViewExpenses =
            findViewById<Button>(R.id.btnViewExpenses)

        val btnReports =
            findViewById<Button>(R.id.btnReports)


        btnCategories.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CategoryActivity::class.java
                )
            )
        }

        btnExpenses.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ExpenseActivity::class.java
                )
            )
        }

        btnGoals.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    GoalActivity::class.java
                )
            )
        }

        btnViewExpenses.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ExpenseListActivity::class.java
                )
            )
        }
        btnReports.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ReportsActivity::class.java
                )
            )
        }
    }
}