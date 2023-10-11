package com.example.mindmaster.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.VideoView
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
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
        val initialScaleX = 0.0f // Anfangs-X-Skalierung (0%, unsichtbar)
        val finalScaleX = 1.0f // End-X-Skalierung (100%, sichtbar)
        val initialScaleY = 1.0f // Anfangs-Y-Skalierung (100%, volle Höhe)
        val finalScaleY = 1.0f // End-Y-Skalierung (100%, volle Höhe)

        val fadeInAnimation = AnimationSet(true)

// Hinzufügen der Skalierungsanimation
        val scaleAnimation = ScaleAnimation(initialScaleX, finalScaleX, initialScaleY, finalScaleY)
        scaleAnimation.duration = 1000
        fadeInAnimation.addAnimation(scaleAnimation)

// Hinzufügen der Drehanimation
        val pivotX = 0.5f // X-Koordinate der Pivot-Achse (0.5 = Mitte der View)
        val pivotY = 0.5f // Y-Koordinate der Pivot-Achse (0.5 = Mitte der View)
        val rotateAnimation = RotateAnimation(0f, -360f, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY)
        rotateAnimation.duration = 2000
        fadeInAnimation.addAnimation(rotateAnimation)

        binding.jokeTV.startAnimation(fadeInAnimation)

// Ausblendanimation nach Verzögerung
        Handler().postDelayed({
            val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    // Die Animation startet
                }

                override fun onAnimationEnd(animation: Animation?) {
                    // Die Animation ist beendet, Sichtbarkeit wird gesetzt
                    binding.jokeTV.visibility = View.INVISIBLE
                    val gifImageView = binding.invisibleImage
                    gifImageView.visibility = View.VISIBLE

                    val gifDrawable = pl.droidsonroids.gif.GifDrawable(
                        resources,
                        R.drawable.gif2
                    )

                    gifImageView.setImageDrawable(gifDrawable)

                    gifDrawable.start() // Startet  die GIF-Animation



                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // Wird bei Wiederholungen der Animation aufgerufen
                }
            })
            binding.jokeTV.startAnimation(fadeOutAnimation)
        }, 8000) // Hier wird die Ausblendung nach 8000 Millisekunden  durchgeführt








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