package com.example.myapplicationbudgetplease.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplicationbudgetplease.database.entities.Goal

@Dao
interface GoalDao {

    @Insert
    suspend fun insert(goal: Goal)

    @Query("SELECT * FROM goals")
    suspend fun getGoals(): List<Goal>
}