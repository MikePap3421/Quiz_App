package com.example.quizappproject.Repositories

import com.example.quizappproject.Dao.CategoryProgressDao
import com.example.quizappproject.Entities.CategoryProgressEntity


class CategoryProgressRepository(private val progressDao: CategoryProgressDao) {

    suspend fun insertProgress(progress: CategoryProgressEntity) {
        progressDao.insertProgress(progress)
    }

    suspend fun updateProgress(progress: CategoryProgressEntity) {
        progressDao.updateProgress(progress)
    }

    fun getProgressByUserAndCategory(userId: Int, categoryName: String): CategoryProgressEntity? {
        return progressDao.getProgressByUserAndCategory(userId, categoryName)
    }

    fun getAllProgressByUser(userId: Int): List<CategoryProgressEntity> {
        return progressDao.getAllProgressByUser(userId)
    }
}
