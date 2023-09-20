package com.example.mindmaster.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mindmaster.R
import com.example.mindmaster.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private val viewModel : MindMasterViewModel by activityViewModels()

    private lateinit var  binding : FragmentRegisterBinding








    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.HomeIV.setImageResource(R.drawable.collab_media_g5wo__xozji_unsplash_2)
        binding.welcomeTV.text= "Willkommen zu deinem MindMaster\n   Quiz App"

        // Um die BottonNavigationBar auszublenden

        val bottomNavigationView = requireActivity().findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE


        binding.anmeldungTV.setOnClickListener {

            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
        }
    }




}