package com.example.mindmaster.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Spinner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.databinding.ListItemCategoryBinding
import com.example.mindmaster.ui.CategoryFragmentDirections
import com.example.mindmaster.ui.MindMasterViewModel
import kotlin.math.min

class CategoryAdapter(

    val viewModel: MindMasterViewModel,
    val categories: List<String>,
    val spinner: Spinner

) : RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemCategoryBinding) :
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
        val category = categories[position]
        viewModel.currentCategory = category
        holder.binding.category2TV.text = category


        val imageResources = viewModel.getImageRessourceId()
        if (position >= 0 && position < imageResources.size) {
            val imageResource = imageResources[position]

            holder.binding.categoryIV.setImageResource(imageResource)

            holder.binding.categoryCV.setOnClickListener {




                val difficulty = spinner.selectedItem.toString()
                viewModel.currentDifficulty = difficulty

                val navController = holder.itemView.findNavController()
                navController.navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToQuizFragment()
                )
            }


        }




    }


    // Hier begrneze ich die ansichten der Kategorien in der Recyclerview auf 9
    override fun getItemCount(): Int {
        return categories.size
    }


}
