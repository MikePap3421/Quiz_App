package com.example.quizappproject.Entities
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val name: String, // e.g. "Math"
    val imageResId: Int           // Local image reference
)
