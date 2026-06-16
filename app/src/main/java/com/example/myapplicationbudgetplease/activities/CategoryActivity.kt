package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.adapters.CategoryAdapter
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import com.example.myapplicationbudgetplease.database.entities.Category
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {

    private lateinit var edtCategory: EditText
    private lateinit var btnAddCategory: Button
    private lateinit var recycler: RecyclerView
    private lateinit var db: BudgetDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        edtCategory = findViewById(R.id.edtCategory)
        btnAddCategory = findViewById(R.id.btnAddCategory)
        recycler = findViewById(R.id.recyclerCategories)

        db = BudgetDatabase.getDatabase(this)

        recycler.layoutManager =
            LinearLayoutManager(this)

        loadCategories()

        btnAddCategory.setOnClickListener {

            val categoryName =
                edtCategory.text.toString().trim()

            if (categoryName.isEmpty()) {
                Toast.makeText(
                    this,
                    "Enter category name",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.categoryDao().insert(
                    Category(name = categoryName)
                )

                loadCategories()

                Toast.makeText(
                    this@CategoryActivity,
                    "Category Added",
                    Toast.LENGTH_SHORT
                ).show()

                edtCategory.setText("")
            }
        }
    }

    private fun loadCategories() {

        lifecycleScope.launch {

            val categories =
                db.categoryDao()
                    .getAllCategories()

            recycler.adapter =
                CategoryAdapter(categories)
        }
    }
}