package com.example.mindmaster.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.mindmaster.R
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.databinding.ListItemCategoryBinding
import com.example.mindmaster.databinding.ListItemHomeBinding
import com.example.mindmaster.ui.MindMasterViewModel

class CategoryAdapter (val data : List<QuestionWithIncorrectAnswers>):RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding: ListItemCategoryBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.ItemViewHolder {
        val binding =
            ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ItemViewHolder, position: Int) {
        val category = data[position]

        holder.binding.categoryIV.setImageResource(R.drawable._4556165_qqvt_fw79_220211)

        val difficultyLevels = arrayOf("Leicht", "Mittel", "Schwer")
        val adapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, difficultyLevels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.binding.spinner.adapter = adapter








    }

    override fun getItemCount(): Int {
     return   data.size
    }




}
