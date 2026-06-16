package com.example.myapplicationbudgetplease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationbudgetplease.R
import com.example.myapplicationbudgetplease.database.entities.Expense
import android.net.Uri
import android.widget.ImageView
class ExpenseAdapter(
    private val expenses: List<Expense>
) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtDescription: TextView =
            view.findViewById(R.id.txtDescription)

        val txtAmount: TextView =
            view.findViewById(R.id.txtAmount)

        val txtDate: TextView =
            view.findViewById(R.id.txtDate)

        val imgExpense: ImageView =
            view.findViewById(R.id.imgExpense)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = expenses.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int

    ) {

        val expense = expenses[position]

        holder.txtDescription.text = expense.description
        holder.txtAmount.text = "R ${expense.amount}"
        holder.txtDate.text = expense.date

        if (expense.imageUri != null) {

            holder.imgExpense.setImageURI(
                Uri.parse(expense.imageUri)
            )
        }
    }
}