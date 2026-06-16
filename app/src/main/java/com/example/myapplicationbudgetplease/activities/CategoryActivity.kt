package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import com.example.myapplicationbudgetplease.database.entities.Category
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {

    private lateinit var edtCategory: EditText
    private lateinit var btnAddCategory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        edtCategory = findViewById(R.id.edtCategory)
        btnAddCategory = findViewById(R.id.btnAddCategory)

        val db = BudgetDatabase.getDatabase(this)

        btnAddCategory.setOnClickListener {

            val categoryName = edtCategory.text.toString().trim()

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

                Toast.makeText(
                    this@CategoryActivity,
                    "Category Added",
                    Toast.LENGTH_SHORT
                ).show()

                edtCategory.setText("")
            }
        }
    }
}