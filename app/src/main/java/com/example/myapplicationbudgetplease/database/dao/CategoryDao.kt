package com.example.myapplicationbudgetplease.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplicationbudgetplease.database.entities.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>
}