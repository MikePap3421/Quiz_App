package com.example.quizappproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.quizappproject.Entities.QuestionEntity
import com.example.quizappproject.Repositories.UserRepository
import com.example.quizappproject.ViewModelFactories.UserViewModelFactory
import com.example.quizappproject.ViewModels.UserViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private fun saveUserSession(email: String) {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("USER_EMAIL", email)
            apply()
        }
    }
    private fun checkUserSession() {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val savedEmail = sharedPref.getString("USER_EMAIL", null)

        if (savedEmail!= null) {
            // User already logged in, go to SecondActivity
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun logoutUser() {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear() // Remove user data
            apply()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close current activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserSession()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<Button>(R.id.btMain)
        val editTextName = findViewById<EditText>(R.id.TvName) // Use EditText for input
        val editTextEmail = findViewById<EditText>(R.id.tvEmail) // Use EditText for input


        // Initialize ViewModel
        val database = AppDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))
            .get(UserViewModel::class.java)
       // val db = AppDatabase.getDatabase(this)
        //insertQuestionsIfFirstTime(this, db)



        button.setOnClickListener {
            val enteredName = editTextName.text.toString()
            val enteredEmail = editTextEmail.text.toString()

            if (enteredName.isNotEmpty() && enteredEmail.isNotEmpty()) {
                lifecycleScope.launch {
                    val foundUser = userViewModel.getUserByEmail(enteredEmail)

                    if (foundUser != null && foundUser.name == enteredName) {
                        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("USERNAME_KEY", enteredName) // Store username
                        editor.apply()

                        saveUserSession(enteredEmail)

                        val intent = Intent(this@MainActivity, SecondActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Invalid credentials or user not registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter both name and email", Toast.LENGTH_SHORT).show()
            }
        }

        val goToSignUp = findViewById<TextView>(R.id.tvGoToSignUp2)

        goToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    /*fun insertQuestionsIfFirstTime(context: Context, database: AppDatabase) {
        val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isInserted = prefs.getBoolean("isQuestionsInserted", false)

        if (!isInserted) {
            val questionsJson = context.assets.open("quiz_questions.json")
                .bufferedReader()
                .use { it.readText() }

            val gson = Gson()
            val questionsType = object : TypeToken<List<QuestionEntity>>() {}.type
            val questions: List<QuestionEntity> = gson.fromJson(questionsJson, questionsType)

            CoroutineScope(Dispatchers.IO).launch {
                database.questionDao().insertQuestions(questions)

                // Set the flag to true
                prefs.edit().putBoolean("isQuestionsInserted", true).apply()
            }
        }
    } */
}