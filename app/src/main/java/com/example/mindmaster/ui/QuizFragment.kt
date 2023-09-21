package com.example.mindmaster.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mindmaster.R
import com.example.mindmaster.databinding.FragmentCategoryBinding
import com.example.mindmaster.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()

    private lateinit var binding: FragmentQuizBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = requireArguments().getString("category")
        viewModel.getQuestionsByCategory(category.toString())

        Log.e("Question","$category")



    }


}