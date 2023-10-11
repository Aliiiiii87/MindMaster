package com.example.mindmaster.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.VideoView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mindmaster.R
import com.example.mindmaster.adapter.HomeAdapter
import com.example.mindmaster.databinding.FragmentHomeBinding


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

            val gifRescourceIds = MutableList(questionResult.size) { R.drawable.marissa }
            val context = requireContext()
            val navController = findNavController()


            binding.homeRV.adapter =
                HomeAdapter(questionResult, gifRescourceIds, context, navController, viewModel)
            binding.userImageIV.setImageResource(R.drawable.vicky_hladynets_c8ta0gwpbqg_unsplash)

        }

        // Einblendanimation
        val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.jokeTV.startAnimation(fadeInAnimation)

        // Ausblendanimation nach Verzögerung
        Handler().postDelayed({
            val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    // Die Animation startet
                }

                override fun onAnimationEnd(animation: Animation?) {
                    // Die Animation ist beendet,Sichtbarkeit wird gesetzt
                    binding.jokeTV.visibility = View.INVISIBLE
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // Wird bei Wiederholungen der Animation aufgerufen
                }
            })
            binding.jokeTV.startAnimation(fadeOutAnimation)
        }, 8000) // Hier wird die Ausblendung nach 8000 Millisekunden (8 Sekunden) durchgeführt


        binding.startBT.setOnClickListener {

            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment())
        }

        viewModel.getQuestions()
        viewModel.getJokes()

        viewModel.joke.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {

                binding.jokeTV.text = it.random().joke
            }

        }


        // Hiermit wird eine normale textview Scrollbar

        binding.jokeTV.movementMethod = ScrollingMovementMethod()


    }


}