package com.example.mindmaster.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


        viewModel.evaluationMessageLiveData.observe(viewLifecycleOwner) { evaluationMessage ->
            // Hier können Sie die Auswertungsnachricht in Ihrem UI anzeigen, z.B. in einem TextView
            binding.courseTV.text = evaluationMessage
        }

        val videoPath = "android.resource://${requireContext().packageName}/${R.raw.money}"

        binding.videoView.setVideoURI(Uri.parse(videoPath))
        binding.videoView.start()


        // Das ist der Wiederholungslistener für das Video
        binding.videoView.setOnCompletionListener { mediyaPlayer->

            mediyaPlayer.start()
        }












    }

  }
