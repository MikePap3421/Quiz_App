package com.example.quizappproject.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappproject.AppDatabase
import com.example.quizappproject.CategoryAdapter
import com.example.quizappproject.CategoryData
import com.example.quizappproject.MainActivity
import com.example.quizappproject.ProfileCategoryAdapter
import com.example.quizappproject.R
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private fun logoutUser(context: Context) {
        val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }


    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: ProfileCategoryAdapter
    private lateinit var userNameTextView: TextView
    private lateinit var userPointsTextView: TextView
    private lateinit var userIconTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        recyclerView = view.findViewById(R.id.categoryRecyclerView)
            ?: throw IllegalStateException("RecyclerView not found in layout")

        userNameTextView = view.findViewById(R.id.userName)
        userPointsTextView = view.findViewById(R.id.userPoints)
        userIconTextView = view.findViewById(R.id.userIcon)

        drawerLayout = requireActivity().findViewById(R.id.mainDrawer)
        navView = requireActivity().findViewById(R.id.top_nav_view)

        val sharedPrefs = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = sharedPrefs.getString("USER_EMAIL", null)

        lifecycleScope.launch {
            if (email != null) {
                val userDao = AppDatabase.getDatabase(requireContext()).userDao()
                val user = userDao.getUserByEmail(email)

                user?.let {
                    userNameTextView.text = it.name
                    userPointsTextView.text = "Points: ${it.points}"
                    userIconTextView.text = it.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
                }
            } else {
                Toast.makeText(context, "No email found in session", Toast.LENGTH_SHORT).show()
            }
        }



        val averageScores = listOf(0.70, 0.60, 0.40, 0.30, 0.0, 0.0)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        categoryAdapter = ProfileCategoryAdapter(CategoryData.categories, averageScores)
        recyclerView.adapter = categoryAdapter

        drawerLayout = requireActivity().findViewById(R.id.mainDrawer)
        navView = requireActivity().findViewById(R.id.top_nav_view)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.HomeView -> {
                    Toast.makeText(context, "Home clicked2", Toast.LENGTH_SHORT).show()
                    val HomeFragment = HomeFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            HomeFragment
                        )
                        .addToBackStack(null) // Optional, for back navigation
                        .commit()
                    true
                }

                R.id.Profileview -> {
                    Toast.makeText(context, "Profile clicked2", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.Aboutview -> {
                    Toast.makeText(context, "About clicked2", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.statsView -> {
                    Toast.makeText(context, "Stats clicked2", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.LogOutview -> {
                    Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()
                    context?.let { logoutUser(it) }
                    true
                }

                else -> false
            }.also {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return view
    }
}


