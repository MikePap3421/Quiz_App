package com.example.quizappproject.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_progress")
data class CategoryProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,                    // <-- Required for per-user progress tracking
    val categoryName: String,          // Can be treated as a foreign key string match
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val completed: Boolean = false
)
