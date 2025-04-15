package com.example.quizappproject.Repositories

import com.example.quizappproject.Dao.UserDao
import com.example.quizappproject.Entities.UserEntity



class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }
}
