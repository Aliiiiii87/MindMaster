package com.example.mindmaster.adapter
import android.view.animation.AnimationUtils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindmaster.R
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult
import com.example.mindmaster.databinding.ListItemHomeBinding


class HomeAdapter (val results : List<QuizResult>, private val gifResourceIds : List<Int>, private val context: Context)
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

        val rotationAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_item)
        holder.binding.pointsImageIV.startAnimation(rotationAnimation)





    }




}
