package com.example.mindmaster.ui


import android.app.Application
import android.net.Uri
import android.util.Log
import android.view.View
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


    private val _videoUri = MutableLiveData<Uri>()
    val videoUri: LiveData<Uri>
        get() = _videoUri


    private val _hideProgressBar = MutableLiveData<Boolean>(false)
    val hideProgressBar: LiveData<Boolean>
        get() = _hideProgressBar

    private val _hideArrows = MutableLiveData<Boolean>(false)
    val hideArrows: LiveData<Boolean>
        get() = _hideArrows


    private val _invisibleButton = MutableLiveData<Boolean>(true)
    val invisibleButton: LiveData<Boolean>
        get() = _invisibleButton

    fun hideButton(hide3: Boolean) {
        _invisibleButton.postValue(hide3)
    }


    fun hideProgressBar(hide1: Boolean) {
        _hideProgressBar.postValue(hide1)

    }

    fun hideArrows(hide2: Boolean) {
        _hideArrows.postValue(hide2)
    }

    fun loadEasyVideo() {

        _videoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderatror1}"))
    }

    fun loadMediumVideo() {
        _videoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderator2}"))
    }

    fun loadHardVideo() {
        _videoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderator3}"))
    }

    fun loadHomeVideo() {
        _videoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderator5}"))

    }

    fun loadLoseVideo() {

        _videoUri.postValue(Uri.parse("android.resource://${getApplication<Application>().packageName}/${R.raw.moderator4}"))
        hideProgressBar(true)
        hideArrows(true)


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

        if (_answerIndex.value!! < (question.value?.size!!)) {
            _questionAnswered.value = false
            if (_answerIndex.value == 0) {
                _currentQuestion.postValue(_answerIndex.value?.let { question.value?.get(it) })
                _answerIndex.postValue(_answerIndex.value!! + 1)
           }else{
               _currentQuestion.postValue(_answerIndex.value?.let { question.value?.get(it) })
               _answerIndex.postValue(_answerIndex.value!! + 1)
            }


        } else {


        }


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


    // Ist die Liste der Preise
    val newImageResourceList = listOf(
        R.drawable.code1,
        R.drawable.code2,
        R.drawable.code3,
        R.drawable.code4,
        R.drawable.code5,
        R.drawable.code6,

        )

    fun getRandomImageResource(): Int {
        if (newImageResourceList.isEmpty()) {
            return 0 // Oder eine Standardbildressourcen-ID, wenn die Liste leer ist
        }

        val randomIndex = (newImageResourceList.indices).random()
        return newImageResourceList[randomIndex]
    }


    fun getQuestionsByCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuestionByCategory(currentCategory, currentDifficulty)
        }
    }

    val categories = repository.categories


    fun showEvaluation(score: Int, difficulty: String) {
        when {
            score >= 1500 && difficulty == "easy" -> {
                loadEasyVideo()


            }

            score >= 3000 && difficulty == "medium" -> {
                loadMediumVideo()


            }

            score >= 4000 && difficulty == "hard" -> {
                loadHardVideo()


            }

            else -> {

                loadLoseVideo()
                hideProgressBar(true)
                hideArrows(true)


            }
        }

    }

    // fun um alle Sonderzeichen zu entfernen
    fun formatText(input: String): String {
        return input.replace(Regex("[^A-Za-z0-9 ]"), "")
    }


}





