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
        viewModel.getQuestionsByCategory(category.toString())






        Log.e("Question", "$category")


        val progressBarHeight =
            resources.getDimensionPixelSize(androidx.transition.R.dimen.abc_progress_bar_height_material)
        val layoutParams = quizProgressBar.layoutParams
        layoutParams.height = progressBarHeight
        quizProgressBar.layoutParams = layoutParams


        startCountdownTimer()


    }

    private fun startCountdownTimer() {



        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.audio)
        mediaPlayer.start()


        // 20 Sekunden, alle 1000 Millisekunden (1 Sekunde) aktualisieren

        countDownTimer = object : CountDownTimer(25000, 1000) {
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



}