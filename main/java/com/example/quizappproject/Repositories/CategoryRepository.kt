package com.example.quizappproject.Repositories

import com.example.quizappproject.Dao.CategoryDao
import com.example.quizappproject.Entities.CategoryEntity


class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    suspend fun insertAll(categories: List<CategoryEntity>) {
        categoryDao.insertAll(categories)
    }

    fun getAllCategories(): List<CategoryEntity> {
        return categoryDao.getAllCategories()
    }

    suspend fun deleteAllCategories() {
        categoryDao.deleteAll()
    }
}
