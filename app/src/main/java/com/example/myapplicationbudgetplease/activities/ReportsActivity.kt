package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import kotlinx.coroutines.launch

class ReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reports)

        val edtStartDate =
            findViewById<EditText>(R.id.edtStartDate)

        val edtEndDate =
            findViewById<EditText>(R.id.edtEndDate)

        val btnGenerateReport =
            findViewById<Button>(R.id.btnGenerateReport)

        val txtReportResults =
            findViewById<TextView>(R.id.txtReportResults)

        val db = BudgetDatabase.getDatabase(this)

        btnGenerateReport.setOnClickListener {

            val startDate =
                edtStartDate.text.toString()

            val endDate =
                edtEndDate.text.toString()

            lifecycleScope.launch {

                val expenses =
                    db.expenseDao()
                        .getExpensesBetweenDates(
                            startDate,
                            endDate
                        )

                var grandTotal = 0.0

                val categoryTotals =
                    mutableMapOf<Int, Double>()

                for (expense in expenses) {

                    grandTotal += expense.amount

                    val currentTotal =
                        categoryTotals[expense.categoryId] ?: 0.0

                    categoryTotals[expense.categoryId] =
                        currentTotal + expense.amount
                }

                val reportBuilder = StringBuilder()

                reportBuilder.append(
                    "Expenses Found: ${expenses.size}\n"
                )

                reportBuilder.append(
                    "Total Spent: R$grandTotal\n\n"
                )

                reportBuilder.append(
                    "Category Totals\n\n"
                )

                for ((categoryId, total) in categoryTotals) {

                    val category =
                        db.categoryDao()
                            .getCategoryById(categoryId)

                    val categoryName =
                        category?.name ?: "Unknown"

                    reportBuilder.append(
                        "$categoryName : R$total\n"
                    )
                }

                txtReportResults.text =
                    reportBuilder.toString()
            }
        }
    }
}