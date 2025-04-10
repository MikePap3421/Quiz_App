package com.example.quizappproject.Repositories

import com.example.quizappproject.Dao.CategoryDao
import com.example.quizappproject.Dao.QuestionDao
import com.example.quizappproject.Entities.CategoryEntity
import com.example.quizappproject.Entities.QuestionEntity


class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun insertQuestion(question: QuestionEntity) {
        questionDao.insertQuestion(question)
    }

    suspend fun insertQuestions(questions: List<QuestionEntity>) {
        questionDao.insertQuestions(questions)
    }

    fun getQuestionsByCategory(categoryName: String): List<QuestionEntity> {
        return questionDao.getQuestionsByCategory(categoryName)
    }
}

