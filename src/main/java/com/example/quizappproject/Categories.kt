package com.example.quizappproject

data class Category(
    val name: String,
    val imageResId: Int
)

object CategoryData {
    val categories = listOf(
        Category("Science", R.drawable.science),
        Category("Flags", R.drawable.flags),
        Category("Geography", R.drawable.geography),
        Category("General Knowledge", R.drawable.general_k),
        Category("History", R.drawable.history),
        Category("Coming soon", R.drawable.comming_soon)
    )
}