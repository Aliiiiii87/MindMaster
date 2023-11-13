package com.example.mindmaster.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.example.mindmaster.R
import com.example.mindmaster.adapter.HomeAdapter
import com.example.mindmaster.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    private var mediaPlayer: MediaPlayer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.home_musik)
        mediaPlayer?.start()

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        // Stoppt die Musik, wenn das Fragment zerstört wird
        mediaPlayer?.stop()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        viewModel.questionResult.observe(viewLifecycleOwner) { questionResult ->

            val gifRescourceIds = MutableList(questionResult.size) { R.drawable.winner8 }
            val context = requireContext()
            val navController = findNavController()


            binding.homeRV.adapter =
                HomeAdapter(questionResult, gifRescourceIds, context, navController, viewModel)



        }
         // implementierung des Indekators und Animation des ein und aus Blenden
        class ScrollIndicatorOnScrollListener(private val scrollIndicator: View) : RecyclerView.OnScrollListener() {
            private var isScrolling = false // Benutzer scrollt
            private val fadeInDuration = 500L // Zeitdauer in Millisekunden für das Einblenden
            private val fadeOutDuration = 500L // Zeitdauer in Millisekunden für das Ausblenden

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Benutzer hat aufgehört zu scrollen
                    isScrolling = false
                    fadeIn()
                } else {
                    // Benutzer scrollt
                    isScrolling = true
                    fadeOut()
                }
            }

            private fun fadeIn() {
                scrollIndicator.animate()
                    .alpha(1f)
                    .setDuration(fadeInDuration)
                    .start()
            }

            private fun fadeOut() {
                scrollIndicator.animate()
                    .alpha(0f)
                    .setDuration(fadeOutDuration)
                    .start()
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        }

        val scrollIndicator = binding.scrollIndicator
        val recyclerView = binding.homeRV
        val scrollListener = ScrollIndicatorOnScrollListener(scrollIndicator)
        recyclerView.addOnScrollListener(scrollListener)










        val fadeInAnimation = AnimationSet(true)

// Skalierungsanimation
        val scaleAnimation = ScaleAnimation(0f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f)
        scaleAnimation.duration = 1000
        fadeInAnimation.addAnimation(scaleAnimation)

// Übersetzungsanimation, um die View von links nach rechts zu bewegen
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, -1f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        )
        translateAnimation.duration = 1000
        fadeInAnimation.addAnimation(translateAnimation)

// Federungseffektanimation
        val overshootInterpolator = OvershootInterpolator(10.0f) // Stärke des Federungseffekts anpassen
        val bounceAnimation = ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        bounceAnimation.duration = 3000
        bounceAnimation.interpolator = overshootInterpolator
        fadeInAnimation.addAnimation(bounceAnimation)

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
                        R.drawable.tv33
                    )

                    gifImageView.setImageDrawable(gifDrawable)

                    gifDrawable.start() // Startet die GIF-Animation
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // Wird bei Wiederholungen der Animation aufgerufen
                }
            })
            binding.jokeTV.startAnimation(fadeOutAnimation)
        }, 2000) // Hier wird die Ausblendung nach 2000 Millisekunden durchgeführt

        binding.startBT.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment())
        }

        viewModel.getQuestions()
        viewModel.getJokes()

        viewModel.joke.observe(viewLifecycleOwner) { jokes ->
            if (jokes.isNotEmpty()) {
                val joke = jokes.random().joke

                // Klick-Handler für die "invisibleImage"
                binding.invisibleImage.setOnClickListener { view ->
                    val dialog = Dialog(requireContext())
                    dialog.setContentView(R.layout.popup_layout)

                    // Hier setzt man die TextView-Ansicht im Dialog-Layout und setzt den Joke-Inhalt
                    val popupJokeTextView = dialog.findViewById<TextView>(R.id.popupText)
                    popupJokeTextView.text = joke

                    // Klick-Handler für den "Schließen"-Button im Dialogfenster
                    val closeButton = dialog.findViewById<Button>(R.id.closeButton)
                    closeButton.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.show()
                }
            }
        }



    }
}