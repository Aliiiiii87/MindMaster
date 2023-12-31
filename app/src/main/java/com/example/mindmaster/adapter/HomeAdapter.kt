package com.example.mindmaster.adapter

import android.view.animation.AnimationUtils

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindmaster.R
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult
import com.example.mindmaster.databinding.ListItemHomeBinding
import com.example.mindmaster.ui.HomeFragmentDirections
import com.example.mindmaster.ui.MindMasterViewModel


class HomeAdapter(

    val results: List<QuizResult>,
    private val gifResourceIds: List<Int>,
    private val context: Context,
    private val navController: NavController,
    private val viewModel: MindMasterViewModel,


    ) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemHomeBinding) :
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

        holder.binding.pointsTv.text = "Punkte: " + questionResult.score.toString()
        holder.binding.categoryTV.text = questionResult.category
        holder.binding.difficultyTv.text = questionResult.difficulty




        holder.binding.pointsImageIV.setImageResource(R.drawable.winner8)

        Glide.with(context).load(gifResourceId).into(holder.binding.pointsImageIV)

        val rotationAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_item)
        holder.binding.pointsImageIV.startAnimation(rotationAnimation)



        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCourseFragment()
            navController.navigate(action)
            viewModel.showEvaluation(questionResult.score, questionResult.difficulty)


            // der Hintergrund ändert sich beim klicken des Items
            holder.itemView.isSelected = !holder.itemView.isSelected


        }


    }


}
