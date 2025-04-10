package com.example.quizappproject.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappproject.Entities.UserEntity
import com.example.quizappproject.Repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return repository.getUserByEmail(email)
    }

    fun getAllUsers(callback: (List<UserEntity>) -> Unit) {
        viewModelScope.launch {
            val users = repository.getAllUsers()
            callback(users)
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }
}
