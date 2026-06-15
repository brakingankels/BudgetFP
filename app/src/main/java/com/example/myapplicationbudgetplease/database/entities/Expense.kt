package com.example.myapplicationbudgetplease.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val description: String,

    val amount: Double,

    val date: String,

    val startTime: String,

    val endTime: String,

    val categoryId: Int,

    val imageUri: String?
)