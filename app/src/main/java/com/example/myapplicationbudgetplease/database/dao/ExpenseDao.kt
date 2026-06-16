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

    @Query("""
    SELECT * FROM expenses
    WHERE date BETWEEN :startDate AND :endDate
""")
    suspend fun getExpensesBetweenDates(
        startDate: String,
        endDate: String
    ): List<Expense>

    @Query("""
    SELECT SUM(amount)
    FROM expenses
    WHERE categoryId = :categoryId
    AND date BETWEEN :startDate AND :endDate
""")
    suspend fun getCategoryTotal(
        categoryId: Int,
        startDate: String,
        endDate: String
    ): Double?
}