package com.example.quizappproject.Repositories

import com.example.quizappproject.Dao.QuestionDao
import com.example.quizappproject.Entities.QuestionEntity

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun getQuestionsForCategory(category: String): List<QuestionEntity> {
        return questionDao.getQuestionsByCategory(category)
    }

    suspend fun insertQuestions(questions: List<QuestionEntity>) {
        questionDao.insertQuestions(questions)
    }

    suspend fun insertQuestion(question: QuestionEntity) {
        questionDao.insertQuestion(question)
    }

    suspend fun getAllQuestions(): List<QuestionEntity> {
        return questionDao.getAllQuestions()
    }

    suspend fun deleteQuestionsForCategory(category: String) {
        questionDao.deleteQuestionsForCategory(category)
    }
}
