package com.example.mindmaster.ui


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindmaster.R
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.IncorrectAnswer
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.database.getInstance
import com.example.mindmaster.remote.JokeApi
import com.example.mindmaster.remote.MindMasterApi
import com.example.mindmaster.remote.MindMasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MindMasterViewModel(application: Application) : AndroidViewModel(application) {

    var database = getInstance(application)

    private val repository = MindMasterRepository(MindMasterApi, JokeApi, database)

    val question = repository.question
    val joke = repository.joke

    var answerIndex = 0

   val _currentQuestion =
        MutableLiveData<QuestionWithIncorrectAnswers>(question.value?.get(answerIndex))
    val currentQuestion: LiveData<QuestionWithIncorrectAnswers>
        get() = _currentQuestion





    fun getQuestions() {


        viewModelScope.launch(Dispatchers.IO) {

            repository.getAllQuestions()
        }


    }

// Todo
    fun nextQuestion() {

        answerIndex++
        _currentQuestion.postValue(question.value?.get(answerIndex))


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


    fun getQuestionsByCategory(category: String, difficulty :String) {
        viewModelScope.launch(Dispatchers.IO) {


           repository.getQuestionByCategory(category,difficulty)


        }

    }

    val categories = repository.categories


}





