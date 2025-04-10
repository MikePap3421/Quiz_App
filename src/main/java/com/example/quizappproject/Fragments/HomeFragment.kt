package com.example.quizappproject.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizappproject.*
import com.google.android.material.navigation.NavigationView

class HomeFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HomeFragment", "onViewCreated: started")

        val context = requireContext()

        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecyclerView)
        val categories = listOf(
            Category("Science", R.drawable.science),
            Category("Flags", R.drawable.flags),
            Category("Geography", R.drawable.geography),
            Category("General Knowledge", R.drawable.general_k),
            Category("History", R.drawable.history),
            Category("Coming soon", R.drawable.comming_soon)
        )
        val adapter = CategoryAdapter(categories) { selectedCategory ->
            val questionFragment = QuestionFragment.newInstance(selectedCategory.name)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, questionFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        drawerLayout = requireActivity().findViewById(R.id.mainDrawer)
        navView = requireActivity().findViewById(R.id.top_nav_view)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.HomeView -> {
                    Toast.makeText(context, "Home clicked1", Toast.LENGTH_SHORT).show()
                    val homeFragment = HomeFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)  // Replace with your fragment container ID
                        .addToBackStack(null) // Optional, for back navigation
                        .commit()
                    true
                }
                R.id.Profileview -> {
                    // Show toast for debugging
                    Toast.makeText(context, "Profile clicked1", Toast.LENGTH_SHORT).show()

                    // Switch to ProfileFragment
                    val profileFragment = ProfileFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, profileFragment)  // Replace with your fragment container ID
                        .addToBackStack(null) // Optional, for back navigation
                        .commit()

                    true
                }
                R.id.Aboutview -> {
                    Toast.makeText(context, "About clicked1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.statsView -> {
                    Toast.makeText(context, "Stats clicked1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.LogOutview -> {
                    Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()
                    logoutUser(context)
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }


        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedUserName = sharedPreferences.getString("USERNAME_KEY", "Guest")
        val firstLetter = savedUserName?.firstOrNull()?.uppercaseChar()?.toString() ?: "?"

        val headerView = navView.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.textView)
        tvUserName.text = savedUserName
    }
}
