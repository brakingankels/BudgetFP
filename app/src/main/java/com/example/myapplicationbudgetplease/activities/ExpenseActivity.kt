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
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.myapplicationbudgetplease.database.entities.Category
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar

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
        edtDate.setOnClickListener {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    val date =
                        String.format(
                            "%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                        )

                    edtDate.setText(date)
                },
                year,
                month,
                day
            ).show()
        }
        edtDate.isFocusable = false
        val edtStartTime = findViewById<EditText>(R.id.edtStartTime)
        edtStartTime.isFocusable = false

        edtStartTime.setOnClickListener {

            val calendar = Calendar.getInstance()

            val hour =
                calendar.get(Calendar.HOUR_OF_DAY)

            val minute =
                calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->

                    edtStartTime.setText(
                        String.format(
                            "%02d:%02d",
                            selectedHour,
                            selectedMinute
                        )
                    )
                },
                hour,
                minute,
                true
            ).show()
        }
        val edtEndTime = findViewById<EditText>(R.id.edtEndTime)
        edtEndTime.isFocusable = false

        edtEndTime.setOnClickListener {

            val calendar = Calendar.getInstance()

            val hour =
                calendar.get(Calendar.HOUR_OF_DAY)

            val minute =
                calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->

                    edtEndTime.setText(
                        String.format(
                            "%02d:%02d",
                            selectedHour,
                            selectedMinute
                        )
                    )
                },
                hour,
                minute,
                true
            ).show()
        }
        val spinnerCategories =
            findViewById<Spinner>(R.id.spinnerCategories)

        val btnSaveExpense = findViewById<Button>(R.id.btnSaveExpense)

        val db = BudgetDatabase.getDatabase(this)
        var categoryList = listOf<Category>()

        lifecycleScope.launch {

            categoryList =
                db.categoryDao()
                    .getAllCategories()

            val categoryNames =
                categoryList.map { it.name }

            val adapter =
                ArrayAdapter(
                    this@ExpenseActivity,
                    android.R.layout.simple_spinner_item,
                    categoryNames
                )

            adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )

            spinnerCategories.adapter = adapter
        }

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


            if (
                description.isEmpty() ||
                amountText.isEmpty() ||
                date.isEmpty() ||
                startTime.isEmpty() ||
                endTime.isEmpty()
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
                        categoryId =
                            categoryList[
                                spinnerCategories.selectedItemPosition
                            ].id,
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
            }
        }
    }
}