package com.example.quizappproject.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappproject.Repositories.QuestionRepository
import com.example.quizappproject.Entities.QuestionEntity
import kotlinx.coroutines.launch

class QuestionViewModel(private val repository: QuestionRepository) : ViewModel() {

    fun insertQuestions(questions: List<QuestionEntity>) {
        viewModelScope.launch {
            repository.insertQuestions(questions)
        }
    }

    suspend fun getQuestionsByCategory(category: String): List<QuestionEntity> {
        return repository.getQuestionsForCategory(category)
    }
}
