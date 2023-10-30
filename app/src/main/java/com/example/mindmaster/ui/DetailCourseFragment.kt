package com.example.mindmaster.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mindmaster.R
import com.example.mindmaster.databinding.FragmentCourseBinding
import com.example.mindmaster.databinding.FragmentDetailCourseBinding

class DetailCourseFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()

    private lateinit var binding: FragmentDetailCourseBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail_course, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}