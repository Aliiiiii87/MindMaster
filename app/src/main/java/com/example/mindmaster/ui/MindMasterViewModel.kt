package com.example.mindmaster.ui


import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

import com.example.mindmaster.R
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult
import com.example.mindmaster.database.getInstance
import com.example.mindmaster.remote.JokeApi
import com.example.mindmaster.remote.MindMasterApi
import com.example.mindmaster.remote.MindMasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class MindMasterViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var currentCategory : String
    lateinit var currentDifficulty : String


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

    fun getCountOfQuizResult():Long{

        return repository.getCountResult()
    }

    fun saveResult(){

       viewModelScope.launch (Dispatchers.IO){

           _playerPoints.value?.let { QuizResult(id = getCountOfQuizResult()+1,category = currentCategory, difficulty = currentDifficulty, score = it) }
               ?.let { repository.insertResult(it) }
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

        Log.e("First", "${_currentQuestion.value}")

        _currentQuestion.postValue(_answerIndex.value?.let { question.value?.get(it) })

    }





    fun nextQuestion() {
        // val delayMillis = 1000
        // Handler().postDelayed({


        if (_answerIndex.value!! < (question.value?.size!!)) {
            _answerIndex.value = _answerIndex.value!! + 1
            _questionAnswered.value = false
            _currentQuestion.postValue(_answerIndex.value?.let { question.value?.get(it) })


        } else {


        }

        // }, delayMillis.toLong())
    }


    fun getQuestions() {


        viewModelScope.launch(Dispatchers.IO) {

            repository.getAllQuestions()
        }


    }

    fun indexReset() {

        _answerIndex.value = 0
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

        R.drawable._4556165_qqvt_fw79_220211,
        R.drawable._0438641_rg8h_nfpw_190730,
        R.drawable._5680158_thiu_v05v_220328,
        R.drawable._9727741_hwfc_7qeh_170727,
        R.drawable._252403_559,
        R.drawable._609235_38252,
        R.drawable._480974_15298,
        R.drawable._702479_59306,
        R.drawable._46471_44702_o48f37
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


}





