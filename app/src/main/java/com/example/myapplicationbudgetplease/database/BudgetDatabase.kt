package com.example.myapplicationbudgetplease.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplicationbudgetplease.database.dao.CategoryDao
import com.example.myapplicationbudgetplease.database.dao.ExpenseDao
import com.example.myapplicationbudgetplease.database.dao.GoalDao
import com.example.myapplicationbudgetplease.database.dao.UserDao
import com.example.myapplicationbudgetplease.database.entities.Category
import com.example.myapplicationbudgetplease.database.entities.Expense
import com.example.myapplicationbudgetplease.database.entities.Goal
import com.example.myapplicationbudgetplease.database.entities.User

@Database(
    entities = [
        User::class,
        Category::class,
        Expense::class,
        Goal::class

    ],
    version = 1,
    exportSchema = false
)
abstract class BudgetDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao

    abstract fun expenseDao(): ExpenseDao

    abstract fun goalDao(): GoalDao

    companion object {

        @Volatile
        private var INSTANCE: BudgetDatabase? = null

        fun getDatabase(context: Context): BudgetDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "budget_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}