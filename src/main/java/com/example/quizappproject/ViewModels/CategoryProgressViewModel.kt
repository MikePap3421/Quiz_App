package com.example.quizappproject.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappproject.Repositories.CategoryProgressRepository
import com.example.quizappproject.Entities.CategoryProgressEntity
import kotlinx.coroutines.launch

class CategoryProgressViewModel(private val repository: CategoryProgressRepository) : ViewModel() {

    fun getProgressByUserAndCategory(userId: Int, category: String): CategoryProgressEntity? {
        return repository.getProgressByUserAndCategory(userId, category)
    }

    fun getAllProgressByUser(userId: Int): List<CategoryProgressEntity> {
        return repository.getAllProgressByUser(userId)
    }

    fun updateProgress(progress: CategoryProgressEntity) {
        viewModelScope.launch {
            repository.updateProgress(progress)
        }
    }
}
