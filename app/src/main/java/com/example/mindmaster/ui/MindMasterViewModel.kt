package com.example.mindmaster.ui


import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindmaster.R
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult
import com.example.mindmaster.database.getInstance
import com.example.mindmaster.remote.JokeApi
import com.example.mindmaster.remote.MindMasterApi
import com.example.mindmaster.remote.MindMasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MindMasterViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var currentCategory: String
    var currentDifficulty: String = ""






    var database = getInstance(application)

    private val repository = MindMasterRepository(MindMasterApi, JokeApi, database)


    val question = repository.question
    val questionResult = repository.questionResult
    val joke = repository.joke


    private val _currentQuestion =
        MutableLiveData<QuestionWithIncorrectAnswers>()
    val currentQuestion: LiveData<QuestionWithIncorrectAnswers>
        get() = _currentQuestion


    private val _playerPoints = MutableLiveData<Int>()
    val playerPoints: LiveData<Int>
        get() = _playerPoints

    init {
        _playerPoints.value = 0
    }


    private val evaluationMessage = MutableLiveData<String>()
    val evaluationMessageLiveData: LiveData<String>
        get() = evaluationMessage


    private val _easyVideoUri = MutableLiveData<Uri>()
    val easyVideoUri: LiveData<Uri>
        get() = _easyVideoUri

    private val _mediumVideoUri = MutableLiveData<Uri>()
    val mediumVideoUri: LiveData<Uri>
        get() = _mediumVideoUri

    private val _hardVideoUri = MutableLiveData<Uri>()
    val hardVideoUri: LiveData<Uri>
        get() = _hardVideoUri

    fun loadEasyVideo() {
        _easyVideoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderator3}"))
    }

    fun loadMediumVideo() {
        _mediumVideoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderator4}"))
    }

    fun loadHardVideo() {
        _hardVideoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderator5}"))
    }



    fun addPoints(points: Int) {
        if (!_questionAnswered.value!!) {
            updatePlayerPoints(points)
            _questionAnswered.value = true
        }
    }

    fun updatePlayerPoints(newPoints: Int) {
        val currentPoints = _playerPoints.value ?: 0
        val updatedPoints = currentPoints + newPoints
        _playerPoints.postValue(updatedPoints)

    }

    fun getCountOfQuizResult(): Long {

        return repository.getCountResult()
    }

    fun saveResult() {

        viewModelScope.launch(Dispatchers.IO) {


            _playerPoints.value?.let {
                QuizResult(
                    id = getCountOfQuizResult() + 1,
                    category = currentCategory,
                    difficulty = currentDifficulty,
                    score = it
                )
            }
                ?.let { repository.insertResult(it) }


            withContext(Dispatchers.Main) {
                _playerPoints.value = 0
            }

        }
    }

    private var _answerIndex = MutableLiveData<Int>(0)
    val answerIndex: LiveData<Int>
        get() = _answerIndex


    private val _questionAnswered = MutableLiveData<Boolean>()
    val questionAnswered: LiveData<Boolean>
        get() = _questionAnswered

    init {
        _questionAnswered.value = false
    }

    fun setCurrentQuestion() {
        _currentQuestion.postValue(_answerIndex.value?.let { question.value?.get(it) })
    }


    fun nextQuestion() {
        // val delayMillis = 1000
        // Handler().postDelayed({
        if (_answerIndex.value!! < (question.value?.size!!)) {
            _questionAnswered.value = false
            _currentQuestion.postValue(_answerIndex.value?.let { question.value?.get(it) })
            _answerIndex.value = _answerIndex.value!! + 1
        } else {


        }

        // }, delayMillis.toLong())
    }


    fun getQuestions() {


        viewModelScope.launch(Dispatchers.IO) {

            repository.getAllQuestions()
        }
    }

    fun finishQuiz() {

        _answerIndex.value = 0
        saveResult()

    }


    fun questionLevels() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuestionsLevelAndCategory()
        }
    }


    fun getJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getJokes()
        }
    }


    val imageResourceId = listOf(

        R.drawable.gif1,
        R.drawable.gif2,
        R.drawable.gif3,
        R.drawable.gif4,
        R.drawable.gif6,
        R.drawable.gif7,
        R.drawable.gif8,
        R.drawable.gif9,
        R.drawable.gif10,
        R.drawable.gif11,
        R.drawable.gif12,
        R.drawable.gif13,
        R.drawable.gif14,
        R.drawable.gif15,
        R.drawable.gif16

    )


    fun getImageRessourceId(): List<Int> {

        return imageResourceId
    }


    fun getQuestionsByCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuestionByCategory(currentCategory, currentDifficulty)
        }
    }

    val categories = repository.categories





    fun showEvaluation(score: Int, difficulty: String) {
        val message = when {
            score >= 1500 && difficulty == "easy" -> {
                loadEasyVideo()
                "Herzlichen Glückwunsch! Sie haben das leichte Quiz bestanden!"
            }
            score >= 4000 && difficulty == "medium" -> {
                loadMediumVideo()
                "Gut gemacht! Sie haben das mittelschwere Quiz bestanden!"
            }
            score >= 5000 && difficulty == "hard" -> {
                loadHardVideo()
                "Bravo! Sie haben das schwierige Quiz bestanden!"
            }
            else -> {
                "Leider haben Sie das Quiz nicht bestanden. Versuchen Sie es erneut."
            }
        }
        evaluationMessage.postValue(message)
    }








}





