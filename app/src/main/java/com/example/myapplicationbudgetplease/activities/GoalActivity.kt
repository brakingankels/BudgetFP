package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import com.example.myapplicationbudgetplease.database.entities.Goal
import kotlinx.coroutines.launch

class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        val edtMinGoal = findViewById<EditText>(R.id.edtMinGoal)
        val edtMaxGoal = findViewById<EditText>(R.id.edtMaxGoal)
        val btnSaveGoal = findViewById<Button>(R.id.btnSaveGoal)

        val db = BudgetDatabase.getDatabase(this)

        btnSaveGoal.setOnClickListener {

            val minGoal = edtMinGoal.text.toString()
            val maxGoal = edtMaxGoal.text.toString()

            if (minGoal.isEmpty() || maxGoal.isEmpty()) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.goalDao().insert(
                    Goal(
                        minimumGoal = minGoal.toDouble(),
                        maximumGoal = maxGoal.toDouble()
                    )
                )

                Toast.makeText(
                    this@GoalActivity,
                    "Goals Saved",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}