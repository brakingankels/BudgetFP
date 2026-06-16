package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.adapters.ExpenseAdapter
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import kotlinx.coroutines.launch

class ExpenseListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_expense_list)

        val recycler =
            findViewById<RecyclerView>(R.id.recyclerExpenses)

        recycler.layoutManager =
            LinearLayoutManager(this)

        lifecycleScope.launch {

            val expenses =
                BudgetDatabase.getDatabase(this@ExpenseListActivity)
                    .expenseDao()
                    .getAllExpenses()

            recycler.adapter =
                ExpenseAdapter(expenses)
        }
    }
}