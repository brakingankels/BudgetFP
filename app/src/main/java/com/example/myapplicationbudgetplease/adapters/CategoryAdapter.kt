package com.example.myapplicationbudgetplease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.entities.Category

class CategoryAdapter(
    private val categories: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtCategoryName: TextView =
            view.findViewById(R.id.txtCategoryName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        categories.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        holder.txtCategoryName.text =
            categories[position].name
    }
}