package com.example.quizappproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileCategoryAdapter(
    private val categories: List<Category>,
    private val averageScores: List<Double>
) : RecyclerView.Adapter<ProfileCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.categoryLabel)
        val average: TextView = view.findViewById(R.id.categoryAverage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        val avg = averageScores.getOrNull(position) ?: 0.0

        holder.name.text = category.name
        holder.average.text = "Avg: %.2f".format(avg)
    }

    override fun getItemCount(): Int = categories.size
}



