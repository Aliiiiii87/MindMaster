package com.example.mindmaster


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.mindmaster.databinding.ActivityMainBinding
import com.example.mindmaster.ui.MindMasterViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MindMasterViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        viewModel.questionLevels()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController =

            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController




        binding.bottomNavigationView.setupWithNavController(navController)



        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.popBackStack(R.id.homeFragment, false)
                        viewModel.loadHomeVideo()

                    }
                    true
                }

                R.id.courseFragment -> {

                    viewModel.hideArrows(true)
                    viewModel.hideProgressBar(true)

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