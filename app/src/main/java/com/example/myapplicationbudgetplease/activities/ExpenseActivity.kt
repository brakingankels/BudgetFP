package com.example.myapplicationbudgetplease.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.BudgetDatabase
import com.example.myapplicationbudgetplease.database.entities.Expense
import kotlinx.coroutines.launch
import android.net.Uri
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class ExpenseActivity : AppCompatActivity() {
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {
                selectedImageUri = uri
                findViewById<ImageView>(R.id.imgPreview)
                    .setImageURI(uri)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        val edtDescription = findViewById<EditText>(R.id.edtDescription)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val edtDate = findViewById<EditText>(R.id.edtDate)
        val edtStartTime = findViewById<EditText>(R.id.edtStartTime)
        val edtEndTime = findViewById<EditText>(R.id.edtEndTime)
        val edtCategoryId = findViewById<EditText>(R.id.edtCategoryId)

        val btnSaveExpense = findViewById<Button>(R.id.btnSaveExpense)

        val db = BudgetDatabase.getDatabase(this)

        val btnChoosePhoto =
            findViewById<Button>(R.id.btnChoosePhoto)

        val imgPreview =
            findViewById<ImageView>(R.id.imgPreview)

        btnChoosePhoto.setOnClickListener {

            imagePickerLauncher.launch("image/*")

        }

        btnSaveExpense.setOnClickListener {

            val description = edtDescription.text.toString().trim()
            val amountText = edtAmount.text.toString().trim()
            val date = edtDate.text.toString().trim()
            val startTime = edtStartTime.text.toString().trim()
            val endTime = edtEndTime.text.toString().trim()
            val categoryIdText = edtCategoryId.text.toString().trim()

            if (
                description.isEmpty() ||
                amountText.isEmpty() ||
                date.isEmpty() ||
                startTime.isEmpty() ||
                endTime.isEmpty() ||
                categoryIdText.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.expenseDao().insert(
                    Expense(
                        description = description,
                        amount = amountText.toDouble(),
                        date = date,
                        startTime = startTime,
                        endTime = endTime,
                        categoryId = categoryIdText.toInt(),
                        imageUri = selectedImageUri?.toString()
                    )
                )

                Toast.makeText(
                    this@ExpenseActivity,
                    "Expense Saved",
                    Toast.LENGTH_SHORT
                ).show()

                edtDescription.setText("")
                edtAmount.setText("")
                edtDate.setText("")
                edtStartTime.setText("")
                edtEndTime.setText("")
                edtCategoryId.setText("")
            }
        }
    }
}