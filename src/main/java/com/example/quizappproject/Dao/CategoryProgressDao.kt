package com.example.quizappproject.Dao

import com.example.quizappproject.Entities.CategoryProgressEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface CategoryProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: CategoryProgressEntity)

    @Update
    suspend fun updateProgress(progress: CategoryProgressEntity)

    @Query("SELECT * FROM category_progress WHERE categoryName = :category")
    suspend fun getProgressForCategory(category: String): CategoryProgressEntity?

    @Query("SELECT * FROM category_progress")
    suspend fun getAllProgress(): List<CategoryProgressEntity>

    @Query("SELECT * FROM category_progress WHERE userId = :userId AND categoryName = :categoryName LIMIT 1")
    fun getProgressByUserAndCategory(userId: Int, categoryName: String): CategoryProgressEntity?

    @Query("SELECT * FROM category_progress WHERE userId = :userId")
    fun getAllProgressByUser(userId: Int): List<CategoryProgressEntity>
}

