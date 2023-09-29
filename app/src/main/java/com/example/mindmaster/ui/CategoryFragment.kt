package com.example.mindmaster.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mindmaster.R
import com.example.mindmaster.adapter.CategoryAdapter
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.databinding.FragmentCategoryBinding
import com.example.mindmaster.databinding.FragmentHomeBinding
import java.util.Locale.Category


class CategoryFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()

    private lateinit var binding: FragmentCategoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            val adapter = CategoryAdapter(viewModel, categories, binding.spinner)
            binding.categoryRV.adapter = adapter



            // Hier wird für den Spinner das Dialogfeld initialsiert

            val difficultyLevels = resources.getStringArray(R.array.difficulty_levels)
            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                difficultyLevels
            )
            spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice)
            binding.spinner.adapter = spinnerAdapter


        }


    }


}





