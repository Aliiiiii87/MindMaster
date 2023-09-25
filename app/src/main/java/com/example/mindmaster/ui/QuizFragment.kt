package com.example.mindmaster.ui

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import com.example.mindmaster.R
import com.example.mindmaster.databinding.FragmentQuizBinding




class QuizFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var quizProgressBar: ProgressBar
    private var countDownTimer: CountDownTimer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        quizProgressBar = binding.progressBar
        return binding.root
    }

    private var isAnswered = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = requireArguments().getString("category")
        val difficulty = requireArguments().getString("difficulty")
        viewModel.getQuestionsByCategory(category.toString(), difficulty.toString())





        viewModel.question.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {

                viewModel.setCurrentQuestion()
            }
        }


        viewModel.currentQuestion.observe(viewLifecycleOwner) {

            if (it != null) {

                val firstQuestion = it.question
                binding.qustionTV.text = firstQuestion.question
                binding.tvAnswerA.text = firstQuestion.correct_answer


                val incorrectAnswers = it.incorrectAnswers
                binding.tvAnswerB.text = incorrectAnswers[0].incorrectAnswer
                binding.tvAnswerC.text = incorrectAnswers[1].incorrectAnswer
                binding.tvAnswerD.text = incorrectAnswers[2].incorrectAnswer


            }


            viewModel.playerPoints.observe(viewLifecycleOwner) { points ->

                binding.scoreTV.text = "Punkte: $points"


            }


        }



        binding.tvAnswerA.setBackgroundResource(R.drawable.background_green)
        binding.tvAnswerA.setOnClickListener {

            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {

                val points = when (difficulty) {
                    "easy" -> 100
                    "medium" -> 200
                    "hard" -> 300
                    else -> 0

                }
                viewModel.addPoints(points)
                viewModel.nextQuestion()
                startCountdownTimer()


            }
        }





        binding.tvAnswerB.setBackgroundResource(R.drawable.background_red)
        binding.tvAnswerB.setOnClickListener {
            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {
            val points = when(difficulty){

                "easy"-> 0
                "medium"->0
                "hard"->0
                else->0
            }

                viewModel.addPoints(points)
                viewModel.nextQuestion()
                startCountdownTimer()

        }
             }

        binding.tvAnswerC.setBackgroundResource(R.drawable.background_red)
        binding.tvAnswerC.setOnClickListener {
            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {
                val points = when(difficulty){

                    "easy"-> 0
                    "medium"->0
                    "hard"->0
                    else->0
                }

                viewModel.addPoints(points)
                viewModel.nextQuestion()
                startCountdownTimer()

            }
        }
        binding.tvAnswerD.setBackgroundResource(R.drawable.background_red)
        binding.tvAnswerD.setOnClickListener {
            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {
                val points = when(difficulty){

                    "easy"-> 0
                    "medium"->0
                    "hard"->0
                    else->0
                }

                viewModel.addPoints(points)
                viewModel.nextQuestion()
                startCountdownTimer()

            }
        }


        val progressBarHeight =
            resources.getDimensionPixelSize(androidx.transition.R.dimen.abc_progress_bar_height_material)
        val layoutParams = quizProgressBar.layoutParams
        layoutParams.height = progressBarHeight
        quizProgressBar.layoutParams = layoutParams


        startCountdownTimer()


    }

    private fun startCountdownTimer() {

        countDownTimer?.cancel()


        // 20 Sekunden, alle 1000 Millisekunden (1 Sekunde) aktualisieren

        countDownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                updateProgressBar(secondsRemaining)



            }

            override fun onFinish() {

                if (!viewModel.questionAnswered.value!!){

                    isAnswered = true
                    val points = 0
                    viewModel.addPoints(points)
                    viewModel.nextQuestion()
                    startCountdownTimer()




                }

            }
        }

        countDownTimer?.start()
    }

    private fun updateProgressBar(secondsRemaining: Int) {
        val maxProgress = 20 // Der Startwert der ProgressBar
        val currentProgress = maxProgress - secondsRemaining

        val animation = ObjectAnimator.ofInt(quizProgressBar, "progress", currentProgress * 5)
        animation.duration = 1000// Dauer einer Sekunde für jede Aktualisierung
        animation.start()


        binding.countDonwTV.text = secondsRemaining.toString()


    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()

    }





}