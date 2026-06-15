package com.example.myapplicationbudgetplease.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplicationbudgetplease.database.entities.Expense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<Expense>
}