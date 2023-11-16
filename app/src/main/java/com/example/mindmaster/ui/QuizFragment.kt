package com.example.mindmaster.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mindmaster.R
import com.example.mindmaster.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private val viewModel: MindMasterViewModel by activityViewModels()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var quizProgressBar: ProgressBar
    private var countDownTimer: CountDownTimer? = null
    private var currentPoints = 0
    private var scoreAnimator = ValueAnimator()
    private var mediaPlayer : MediaPlayer? = null





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        quizProgressBar = binding.quizPB
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.quiz_musik)
        mediaPlayer?.start()
        return binding.root


    }

    override fun onPause() {
        super.onPause()
        // Pausiere die Musik, wenn das Fragment pausiert wird (z. B. bei der Navigation)
        mediaPlayer?.pause()
    }


    private var isAnswered = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialisiere die VideoView und verknüpfe sie mit der XML-Ansicht
        val animation = binding.animierteVW

        // Stelle sicher, dass das Video aus dem raw-Ressourcenordner geladen wird
        val videoUri =
            Uri.parse("android.resource://${requireContext().packageName}/${R.raw.quiztv}")

        // Setze die Videoquelle der VideoView
        animation.setVideoURI(videoUri)


        animation.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.start()
        }

        // Startet die Wiedergabe des Videos
        animation.start()



        viewModel.getQuestionsByCategory()

        viewModel.question.observe(viewLifecycleOwner) {

            Log.d("LiveDataLog","question")
            if (it.isNotEmpty()) {

                viewModel.setCurrentQuestion()
            }
        }


        // Wenn das Quiz fertig ist soll er dass Ergebnis abspeichern und zum Homefragment naviegieren und mir das Ergebnis anzeigen
        viewModel.answerIndex.observe(viewLifecycleOwner) { index ->
            val questionCount = viewModel.question.value?.size ?: 0
            if (questionCount != 0 && questionCount <= index) {
                viewModel.finishQuiz()
                findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToHomeFragment())

            }

        }

        viewModel.currentQuestion.observe(viewLifecycleOwner) { question ->

            if (question != null) {

                val firstQuestion = question.question
                val formattedQuestion = viewModel.formatText(firstQuestion.question)
                binding.qustionTV.text = formattedQuestion
                binding.tvAnswerA.text = viewModel.formatText(firstQuestion.correct_answer)

                val incorrectAnswers = question.incorrectAnswers
                binding.tvAnswerB.text = viewModel.formatText(incorrectAnswers[0].incorrectAnswer)
                binding.tvAnswerC.text = viewModel.formatText(incorrectAnswers[1].incorrectAnswer)
                binding.tvAnswerD.text = viewModel.formatText(incorrectAnswers[2].incorrectAnswer)
            }

            viewModel.playerPoints.observe(viewLifecycleOwner) { points ->

                Log.d("LiveDataLog","playerpoints")

                createScoreAnimation()
                binding.scoreTV.text = "Punkte:$points"
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

                val mediaplayer = MediaPlayer.create(requireContext(), R.raw.points)
                mediaplayer.start()
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
        val currentProgress = (maxProgress * 5 )- (secondsRemaining * 5 )

        val progress = maxOf(0,currentProgress)

        val animation = ObjectAnimator.ofInt(quizProgressBar, "progress", progress)
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