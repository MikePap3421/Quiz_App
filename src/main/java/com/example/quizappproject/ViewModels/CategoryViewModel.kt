package com.example.quizappproject.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappproject.Repositories.CategoryRepository
import com.example.quizappproject.Entities.CategoryEntity
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    fun insertAll(categories: List<CategoryEntity>) {
        viewModelScope.launch {
            repository.insertAll(categories)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAllCategories()
        }
    }

    // Add more methods if needed
}
