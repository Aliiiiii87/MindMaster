package com.example.mindmaster

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mindmaster.databinding.ActivityMainBinding
import com.example.mindmaster.ui.MindMasterViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MindMasterViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("MainActivity", "Erstellt")

        super.onCreate(savedInstanceState)




        viewModel.question.observe(this){

            Log.e("Datentest",it.toString())
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController =

            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController

        binding.bottomNavigationView.setupWithNavController(navController)



        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.homeFragment -> {

                    navController.popBackStack(R.id.homeFragment, false)

                }

                R.id.courseFragment -> {

                    navController.navigate(R.id.courseFragment)
                    false


                }

                else -> {

                    navController.navigate(R.id.detailCourseFragment)
                    false
                }
            }


        }


    }


}