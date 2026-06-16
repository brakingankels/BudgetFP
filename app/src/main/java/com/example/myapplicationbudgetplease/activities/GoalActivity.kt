package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
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

        val edtMinGoal =
            findViewById<EditText>(R.id.edtMinGoal)

        val edtMaxGoal =
            findViewById<EditText>(R.id.edtMaxGoal)

        val btnSaveGoal =
            findViewById<Button>(R.id.btnSaveGoal)

        val progressGoal =
            findViewById<ProgressBar>(R.id.progressGoal)

        val txtProgressDetails =
            findViewById<TextView>(R.id.txtProgressDetails)

        val db =
            BudgetDatabase.getDatabase(this)

        btnSaveGoal.setOnClickListener {

            val minGoal =
                edtMinGoal.text.toString().trim()

            val maxGoal =
                edtMaxGoal.text.toString().trim()

            if (
                minGoal.isEmpty() ||
                maxGoal.isEmpty()
            ) {

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

                loadGoalProgress(
                    db,
                    progressGoal,
                    txtProgressDetails
                )
            }
        }

        lifecycleScope.launch {

            loadGoalProgress(
                db,
                progressGoal,
                txtProgressDetails
            )
        }
    }

    private suspend fun loadGoalProgress(
        db: BudgetDatabase,
        progressGoal: ProgressBar,
        txtProgressDetails: TextView
    ) {

        val expenses =
            db.expenseDao()
                .getAllExpenses()

        var totalSpent = 0.0

        for (expense in expenses) {
            totalSpent += expense.amount
        }

        val goal =
            db.goalDao()
                .getLatestGoal()

        if (goal != null) {

            val percentage =
                if (goal.maximumGoal > 0) {
                    ((totalSpent / goal.maximumGoal) * 100)
                        .toInt()
                        .coerceAtMost(100)
                } else {
                    0
                }

            progressGoal.progress =
                percentage

            val status = when {

                totalSpent < goal.minimumGoal ->
                    "Below Minimum Goal"

                totalSpent <= goal.maximumGoal ->
                    "Within Goal Range"

                else ->
                    "Above Maximum Goal"
            }

            txtProgressDetails.text =
                "Spent: R$totalSpent\n" +
                        "Min Goal: R${goal.minimumGoal}\n" +
                        "Max Goal: R${goal.maximumGoal}\n" +
                        "$percentage% of maximum goal used\n\n" +
                        status
        }
        else {

            txtProgressDetails.text =
                "No goals saved yet."
        }
    }
}