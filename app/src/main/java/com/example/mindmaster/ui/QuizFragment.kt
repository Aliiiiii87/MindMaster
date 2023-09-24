package com.example.mindmaster.ui

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import com.example.mindmaster.R
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = requireArguments().getString("category")
        val difficulty = requireArguments().getString("difficulty")
        viewModel.getQuestionsByCategory(category.toString(), difficulty.toString())


        viewModel.question.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {

                val firstQuestion = it[1].question
                binding.qustionTV.text = firstQuestion.question
                binding.tvAnswerA.text = firstQuestion.correct_answer


                val incorrectAnswers = it[1].incorrectAnswers
                binding.tvAnswerB.text = incorrectAnswers[0].incorrectAnswer
                binding.tvAnswerC.text = incorrectAnswers[1].incorrectAnswer
                binding.tvAnswerD.text = incorrectAnswers[2].incorrectAnswer


            }


            viewModel.playerPoints.observe(viewLifecycleOwner) { points ->

                binding.scoreTV.text = "Punkte: $points"


            }


        }




        viewModel.question.observe(viewLifecycleOwner) { questionsWithAnswers ->
            if (questionsWithAnswers.isNotEmpty()) {
                val firstQuestionWithAnswers = questionsWithAnswers[0]
                val question = firstQuestionWithAnswers.question
                val difficulty = question.difficulty

                binding.tvAnswerA.setOnClickListener {


                    val points = when (difficulty) {
                        "easy" -> 100
                        "medium" -> 200
                        "hard" -> 300
                        else -> 0
                    }

                    viewModel.addPoints(points)


                }


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


        // 20 Sekunden, alle 1000 Millisekunden (1 Sekunde) aktualisieren

        countDownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                updateProgressBar(secondsRemaining)
            }

            override fun onFinish() {
                // Der Timer ist abgelaufen, führe hier deine Aktionen aus
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

//    private fun checkIfAnswerIsCorrect(selectedAnswer: String, correctAnswer: String): Boolean {
//        return selectedAnswer == correctAnswer
//    }


//    var questionAnswered = false


//    binding.tvAnswerA.setOnClickListener {
//        if (!questionAnswered) {
//            val selectedAnswer = binding.tvAnswerA.toString()
//            val currentQuestion = viewModel.currentQuestion.value
//            val correctAnswer = currentQuestion?.question?.correct_answer
//
//            if (correctAnswer != null) {
//                val isCorrect = checkIfAnswerIsCorrect(selectedAnswer, correctAnswer)
//
//                if (isCorrect) {
//                    binding.tvAnswerA.setBackgroundResource(R.color.red)
//                } else {
//                    binding.tvAnswerA.setBackgroundResource(R.color.orange)
//                }
//
//                val points = when (difficulty) {
//                    "easy" -> 100
//                    "medium" -> 200
//                    "hard" -> 300
//                    else -> 0
//                }
//                viewModel.addPoints(points)
//
//                questionAnswered = true
//            }
//        }
//    }



}