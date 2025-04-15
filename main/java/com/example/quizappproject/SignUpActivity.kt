package com.example.quizappproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.example.quizappproject.Entities.UserEntity
import com.example.quizappproject.Repositories.UserRepository
import com.example.quizappproject.ViewModelFactories.UserViewModelFactory
import com.example.quizappproject.ViewModels.UserViewModel
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private fun saveUserSession(email: String) {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("USER_EMAIL", email)
            apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize ViewModel
        val database = AppDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))
            .get(UserViewModel::class.java)

        val signupButton = findViewById<Button>(R.id.btMainS)
        val nameEditText = findViewById<EditText>(R.id.TvName)
        val emailEditText = findViewById<EditText>(R.id.tvEmail)

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    val existingUser = userViewModel.getUserByEmail(email) // No callback needed
                    if (existingUser == null) {
                        val newUser = UserEntity(name = name, email = email, points = 0)
                        userViewModel.insertUser(newUser)
                        saveUserSession(email)
                        runOnUiThread {
                            Toast.makeText(this@SignUpActivity, "Signup successful!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@SignUpActivity, "User already exists!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            } else {
                Toast.makeText(this, "Please enter both name and email.", Toast.LENGTH_SHORT).show()
            }
        }

        val goToLogin = findViewById<TextView>(R.id.tvGoToSignUp2)
        goToLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}
