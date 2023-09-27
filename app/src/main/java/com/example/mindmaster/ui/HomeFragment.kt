package com.example.mindmaster.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mindmaster.R
import com.example.mindmaster.adapter.HomeAdapter
import com.example.mindmaster.data.dataQuestionModels.QuestionResponse
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.Joke
import com.example.mindmaster.databinding.FragmentHomeBinding
import com.example.mindmaster.remote.JokeApi


class HomeFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE


        viewModel.questionResult.observe(viewLifecycleOwner) { questionResult ->

            binding.homeRV.adapter = HomeAdapter(questionResult)
            binding.userImageIV.setImageResource(R.drawable.vicky_hladynets_c8ta0gwpbqg_unsplash)


        }




        binding.startBT.setOnClickListener {

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment())
        }

        viewModel.getQuestions()
        viewModel.getJokes()

        viewModel.joke.observe(viewLifecycleOwner) {
           if(it.isNotEmpty()){

               binding.jokeTV.text = it.random().joke
           }

        }


        // Hiermit wird eine normale textview Scrollbar

        binding.jokeTV.movementMethod = ScrollingMovementMethod()


    }


}