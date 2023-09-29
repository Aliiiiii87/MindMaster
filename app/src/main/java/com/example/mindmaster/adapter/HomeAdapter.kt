package com.example.mindmaster.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindmaster.R
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult
import com.example.mindmaster.databinding.ListItemHomeBinding

class HomeAdapter (val results : MutableList<QuizResult>, private val gifResourceIds : List<Int>, private val context: Context)
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
        val gifResourceId = gifResourceIds[position]

        holder.binding.pointsTV.text = "Punkte: "+questionResult.score.toString()
        holder.binding.categoryTV.text = questionResult.category
        holder.binding.difficultyTV.text = "Level: "+questionResult.difficulty




        holder.binding.pointsImageIV.setImageResource(R.drawable.marissa)

        Glide.with(context).load(gifResourceId).into(holder.binding.pointsImageIV)


        holder.binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Hier können Sie den gewünschten Wisch-Entfernen-Effekt hinzufügen
                results.removeAt(position)
                notifyItemRemoved(position)
                true
            } else {
                false
            }
        }



    }




}
