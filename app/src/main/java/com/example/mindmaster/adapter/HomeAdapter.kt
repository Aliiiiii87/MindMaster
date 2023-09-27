package com.example.mindmaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mindmaster.R
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult
import com.example.mindmaster.databinding.ListItemHomeBinding

class HomeAdapter (val results : List<QuizResult>)
    : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>(){


        inner class ItemViewHolder(val binding: ListItemHomeBinding):
                RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ListItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val questionResult = results[position]

        holder.binding.pointsTV.text = questionResult.score.toString()
        holder.binding.categoryTV.text = questionResult.category


        holder.binding.pointsImageIV.setImageResource(R.drawable.baseline_monetization_on_24)






    }
}
