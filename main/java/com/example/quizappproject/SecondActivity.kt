package com.example.quizappproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.compose.material3.Text
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.text.color
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.quizappproject.fragments.HomeFragment
import com.example.quizappproject.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch


class SecondActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var userIconTextView: TextView
    private lateinit var userPointsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById(R.id.mainDrawer)
        Log.d("SecondActivity", "onCreate: drawerLayout = $drawerLayout")  // Log statement

        navView = findViewById(R.id.top_nav_view)

        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open, R.string.close
        )


        drawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.white, null)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        val drawerIcon = toolbar.navigationIcon
        drawerIcon?.let {
            toolbar.navigationIcon = it
            toolbar.post {
                val navIconView = toolbar.findViewById<View>(androidx.appcompat.R.id.action_bar)
                    ?: toolbar.getChildAt(1) // fallback
                navIconView?.y= 140.0F
                navIconView?.scaleX = 1.8f
                navIconView?.scaleY = 1.8f
            }
        }


        Log.d("SecondActivity", "DrawerToggle setup complete")

        // Show HomeFragment by default
        loadFragment(HomeFragment())

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.HomeView -> {
                    loadFragment(HomeFragment())
                }
                R.id.Profileview -> {
                    loadFragment(ProfileFragment())
                }
                R.id.Aboutview -> {
                    // TODO: Load AboutFragment
                }
                R.id.statsView -> {
                    // TODO: Load StatsFragment
                }
                R.id.LogOutview -> {
                    logoutUser()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        userPointsTextView = findViewById(R.id.userPoints)
        userIconTextView = findViewById(R.id.userIcon)

        val sharedPrefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = sharedPrefs.getString("USER_EMAIL", null)

        lifecycleScope.launch {
            if (email != null) {
                val userDao = AppDatabase.getDatabase(applicationContext).userDao()
                val user = userDao.getUserByEmail(email)

                user?.let {
                    userIconTextView.text = it.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
                    userPointsTextView.text = "Points: ${it.points}"
                }
            } else {
                Toast.makeText(this@SecondActivity, "No email found in session", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun logoutUser() {
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}