package com.example.mindmaster.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mindmaster.R
import com.example.mindmaster.databinding.FragmentQuizBinding
import kotlin.time.Duration.Companion.seconds


class QuizFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var quizProgressBar: ProgressBar
    private var countDownTimer: CountDownTimer? = null
    private var currentPoints = 0
    private var scoreAnimator = ValueAnimator()


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



        viewModel.getQuestionsByCategory()







        viewModel.question.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {

                viewModel.setCurrentQuestion()
            }
        }




        viewModel.answerIndex.observe(viewLifecycleOwner) { index ->
            viewModel.question.observe(viewLifecycleOwner) { question ->
                if (question.size <= (index)) {


                    viewModel.saveResult()
                    findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToHomeFragment())

                    viewModel.indexReset()

                } else {


                }
            }
        }



        viewModel.currentQuestion.observe(viewLifecycleOwner) {question->

            if (question != null) {

                val firstQuestion = question.question
                binding.qustionTV.text = firstQuestion.question
                binding.tvAnswerA.text = firstQuestion.correct_answer


                val incorrectAnswers = question.incorrectAnswers
                binding.tvAnswerB.text = incorrectAnswers[0].incorrectAnswer
                binding.tvAnswerC.text = incorrectAnswers[1].incorrectAnswer
                binding.tvAnswerD.text = incorrectAnswers[2].incorrectAnswer


            }




            viewModel.playerPoints.observe(viewLifecycleOwner) { points ->


                createScoreAnimation()
                binding.scoreTV.text = "Punkte: $points"


            }


        }




        binding.tvAnswerA.setOnClickListener {

            val scaleAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.text_scale)
            binding.scoreTV.startAnimation(scaleAnimation)

            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {


                val points = when (viewModel.currentDifficulty) {
                    "easy" -> 100
                    "medium" -> 200
                    "hard" -> 300
                    else -> 0

                }

//                val mediaplayer = MediaPlayer.create(requireContext(),R.raw.points)
//                mediaplayer.start()
                increasePoints(points)
                viewModel.addPoints(points)
                viewModel.nextQuestion()
                startCountdownTimer()


            }
        }




        binding.tvAnswerB.setOnClickListener {
            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {
                val points = when (viewModel.currentDifficulty) {

                    "easy" -> 0
                    "medium" -> 0
                    "hard" -> 0
                    else -> 0
                }
                val mediaplayer = MediaPlayer.create(requireContext(), R.raw.wrong)
                mediaplayer.start()
                viewModel.addPoints(points)
                viewModel.nextQuestion()
                startCountdownTimer()

            }
        }


        binding.tvAnswerC.setOnClickListener {
            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {
                val points = when (viewModel.currentDifficulty) {

                    "easy" -> 0
                    "medium" -> 0
                    "hard" -> 0
                    else -> 0
                }
                val mediaplayer = MediaPlayer.create(requireContext(), R.raw.wrong)
                mediaplayer.start()
                viewModel.addPoints(points)
                viewModel.nextQuestion()
                startCountdownTimer()

            }
        }

        binding.tvAnswerD.setOnClickListener {
            countDownTimer?.cancel()
            val isQuestionAnswered = viewModel.questionAnswered.value ?: false
            if (!isQuestionAnswered) {
                val points = when (viewModel.currentDifficulty) {

                    "easy" -> 0
                    "medium" -> 0
                    "hard" -> 0
                    else -> 0
                }

                val mediaplayer = MediaPlayer.create(requireContext(), R.raw.wrong)
                mediaplayer.start()
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
        countDownTimer = object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                updateProgressBar(secondsRemaining)


            }

            override fun onFinish() {

                if (!viewModel.questionAnswered.value!!) {

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


    private fun createScoreAnimation() {
        scoreAnimator = ValueAnimator.ofInt(currentPoints)
        scoreAnimator.duration = 2000
        scoreAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            binding.scoreTV.text = "Punkte: $animatedValue"
        }
    }

    private fun increasePoints(points: Int) {
        currentPoints += points

        // Starte die Animation für den Punktestand
        scoreAnimator.setIntValues(currentPoints - points, currentPoints)
        scoreAnimator.start()
    }


}