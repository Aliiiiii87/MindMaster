package com.example.mindmaster.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.mindmaster.R
import com.example.mindmaster.databinding.FragmentCourseBinding


class CourseFragment : Fragment() {


    private val viewModel: MindMasterViewModel by activityViewModels()

    private lateinit var binding: FragmentCourseBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.evaluationMessageLiveData.observe(viewLifecycleOwner) { evaluationMessage ->
//            // Hier kann man  die Auswertungsnachricht im  UI anzeigen
//            binding.courseTV.text = evaluationMessage
//
//        }





        // binding von der progressbar und imagview und die Animation für den Ladebalken

        val progressBar = binding.quizPB
        val textView1 = binding.textView1
        val textView2 = binding.textView2
        val textView3 = binding.textView3




        val screenWidth = resources.displayMetrics.widthPixels

// Verschiebe die TextViews aus dem Bildschirm (rechts)
        textView1.translationX = screenWidth.toFloat()
        textView2.translationX = screenWidth.toFloat()
        textView3.translationX = screenWidth.toFloat()

        val delayMillis = 4000 // 4 Sekunden
        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.INVISIBLE

            // Erstelle Animationen für die TextViews
            val animation1 = ObjectAnimator.ofFloat(textView1, "translationX", 0f)
            animation1.duration = 1000 // Dauer der Animation in Millisekunden

            val animation2 = ObjectAnimator.ofFloat(textView2, "translationX", 0f)
            animation2.duration = 1000

            val animation3 = ObjectAnimator.ofFloat(textView3, "translationX", 0f)
            animation3.duration = 1000




            // Setze die Sichtbarkeit der TextViews auf sichtbar
            textView1.visibility = View.VISIBLE
            textView2.visibility = View.VISIBLE
            textView3.visibility = View.VISIBLE

            // Erstelle eine AnimatorSet, um die Animationen gemeinsam auszuführen
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animation1, animation2, animation3)

            // Starte die Animationen
            animatorSet.start()
        }, delayMillis.toLong())




        viewModel.hideProgressBar.observe(viewLifecycleOwner) { hide ->
            if (hide) {
                binding.quizPB.visibility = View.INVISIBLE
            }
        }


        viewModel.hideArrows.observe(viewLifecycleOwner) { hide2 ->
            if (hide2) {
                binding.textView1.visibility = View.INVISIBLE
                binding.textView2.visibility = View.INVISIBLE
                binding.textView3.visibility = View.INVISIBLE
            }
        }



        // Klick-Listener für TextView1
        textView1.setOnClickListener {
            // Setze die Sichtbarkeit der ImageView auf sichtbar
            binding.hiddenImageView.visibility = View.VISIBLE
            binding.hiddenImageView.setImageResource(R.drawable.gif8)
            // Setze die Sichtbarkeit der TextViews auf unsichtbar
            textView1.visibility = View.INVISIBLE
            textView2.visibility = View.INVISIBLE
            textView3.visibility = View.INVISIBLE
        }

// Klick-Listener für TextView2
        textView2.setOnClickListener {
            // Setze die Sichtbarkeit der ImageView auf sichtbar
           binding.hiddenImageView.visibility = View.VISIBLE
            binding.hiddenImageView.setImageResource(R.drawable.gif15)
            // Setze die Sichtbarkeit der TextViews auf unsichtbar
            textView1.visibility = View.INVISIBLE
            textView2.visibility = View.INVISIBLE
            textView3.visibility = View.INVISIBLE
        }

// Klick-Listener für TextView3
        textView3.setOnClickListener {
            // Setze die Sichtbarkeit der ImageView auf sichtbar
            binding.hiddenImageView.visibility = View.VISIBLE
            binding.hiddenImageView.setImageResource(R.drawable.gif17)
            // Setze die Sichtbarkeit der TextViews auf unsichtbar
            textView1.visibility = View.INVISIBLE
            textView2.visibility = View.INVISIBLE
            textView3.visibility = View.INVISIBLE
        }



        val videoPath = "android.resource://${requireContext().packageName}/${R.raw.moderator5}"
        val videoView = binding.videoView
        videoView.setVideoURI(Uri.parse(videoPath))

        videoView.setOnPreparedListener { mediaPlayer ->
            // Der onPreparedListener wird nur einmal gesetzt, wenn das Hauptvideo vorbereitet ist.
            mediaPlayer.start()
        }

        viewModel.videoUri.observe(viewLifecycleOwner) { uri ->

            videoView.setVideoURI(uri)
            if (uri != null && uri == viewModel.videoUri.value) {
//                binding.courseTV.text =
                    "Hey Los gehts! Du musst auf ein absolviertes Quiz klicken im Homebereich, um dir die Video-Auswertung anzuschauen."
            } else {

            }
        }
    }





}

