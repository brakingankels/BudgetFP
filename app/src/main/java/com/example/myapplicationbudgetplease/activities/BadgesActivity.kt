package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import kotlinx.coroutines.launch

class BadgesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_badges)

        val txtBadges =
            findViewById<TextView>(R.id.txtBadges)

        val db =
            BudgetDatabase.getDatabase(this)

        lifecycleScope.launch {

            val expenses =
                db.expenseDao()
                    .getAllExpenses()

            val count =
                expenses.size

            val badges =
                StringBuilder()

            badges.append("🏅 Badges Earned\n\n")

            if (count >= 1)
                badges.append("✅ First Expense\n")

            if (count >= 5)
                badges.append("✅ Expense Tracker\n")

            if (count >= 10)
                badges.append("✅ Budget Master\n")

            if (count == 0)
                badges.append("No badges yet")

            txtBadges.text =
                badges.toString()
        }
    }
}