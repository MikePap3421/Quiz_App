package com.example.quizappproject.ViewModelFactories
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizappproject.Repositories.CategoryProgressRepository
import com.example.quizappproject.Repositories.QuestionRepository
import com.example.quizappproject.ViewModels.CategoryProgressViewModel
import com.example.quizappproject.ViewModels.QuestionViewModel

class CategoryProgressViewModelFactory(private val repository: CategoryProgressRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryProgressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryProgressViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
