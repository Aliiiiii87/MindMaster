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


        val slideTextAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_text)
        //Hier  Weise ich  die Animation der TextView zu
        binding.punktestandTV.startAnimation(slideTextAnimation)
        // Verzögerung, bevor die Animation wiederholt wird
        val animationRepeatInterval = 1000000L
        val handler = Handler()
        handler.postDelayed({
            // Hier Startet  die Animation erneut, nachdem die Verzögerung abgelaufen ist
            binding.punktestandTV.startAnimation(slideTextAnimation)
        }, animationRepeatInterval)


        val textView = binding.jokeTV
        var isZoomedIn = false

        textView.setOnClickListener {
            if (isZoomedIn) {
                // Animation, um den Text kleiner zu skalieren
                val scaleDownX = ObjectAnimator.ofFloat(textView, View.SCALE_X, 0.8f)
                val scaleDownY = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 0.8f)

                val scaleDown = AnimatorSet()
                scaleDown.playTogether(scaleDownX, scaleDownY)
                scaleDown.duration = 300

                // Animation, um den Text nach links außerhalb des Bildschirms zu verschieben
                val translateOut = TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
                )
                translateOut.duration = 300

                // Animation, um den Text wieder sichtbar zu machen
                val alphaIn = ObjectAnimator.ofFloat(textView, View.ALPHA, 1.0f)
                alphaIn.duration = 300

                val animationSet = AnimatorSet()
                animationSet.playTogether(scaleDown, alphaIn)

                animationSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        // Hier können Sie den Text in der TextView aktualisieren, wenn nötig
                        // Zum Beispiel: textView.text = "Neuer Text"
                    }
                })

                animationSet.start()
                isZoomedIn = false
            } else {
                // Animation, um den Text zu vergrößern
                val scaleUpX = ObjectAnimator.ofFloat(textView, View.SCALE_X, 1.2f)
                val scaleUpY = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 1.2f)

                val scaleUp = AnimatorSet()
                scaleUp.playTogether(scaleUpX, scaleUpY)
                scaleUp.duration = 300

                // Animation, um den Text von links auf den Bildschirm zu verschieben
                val translateIn = TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
                )
                translateIn.duration = 300

                // Animation, um den Text sichtbar zu machen
                val alphaIn = ObjectAnimator.ofFloat(textView, View.ALPHA, 1.0f)
                alphaIn.duration = 300

                val animationSet = AnimatorSet()
                animationSet.playTogether(scaleUp, alphaIn)

                animationSet.start()
                isZoomedIn = true
            }
        }










        viewModel.questionResult.observe(viewLifecycleOwner) { questionResult ->

            val gifRescourceIds = MutableList(questionResult.size) { R.drawable.marissa }
            val context = requireContext()
            val navController = findNavController()





            binding.homeRV.adapter =
                HomeAdapter(questionResult, gifRescourceIds, context, navController, viewModel)
            binding.userImageIV.setImageResource(R.drawable.vicky_hladynets_c8ta0gwpbqg_unsplash)

        }








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