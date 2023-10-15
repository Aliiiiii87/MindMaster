package com.example.mindmaster.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.activityViewModels
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

        val videoPath = "android.resource://${requireContext().packageName}/${R.raw.moderator14}"
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

