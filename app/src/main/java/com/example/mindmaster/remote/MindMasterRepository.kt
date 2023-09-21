package com.example.mindmaster.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.QuestionResponse
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.IncorrectAnswer
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.Joke
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.database.MindMasterDatabase
import java.lang.Exception

class MindMasterRepository(
    private val api1: MindMasterApi, private val api2: JokeApi,
    private var database: MindMasterDatabase
) {


    private val _question = database.mindMasterDao.getAllQuestion()
    val question: LiveData<List<QuestionWithIncorrectAnswers>>
        get() = _question


    private val _joke = database.mindMasterDao.getAllJokes()
    val joke: LiveData<List<Joke>>
        get() = _joke


    val categories = database.mindMasterDao.getCategories()





// toDo für die Quiz
    suspend fun getQuestionByCategory(category: String): List<Question> {
        return database.mindMasterDao.getQuestionByCategory(category)
    }




    suspend fun getJokes() {
        try {

            val joke = api2.retrofitService.getJokes()

            database.mindMasterDao.insertJoke(joke)


        } catch (e: Exception) {

            Log.e("JokeApiservice", "Error $e")
        }
    }




    suspend fun getAllQuestions() {

        try {

            val questionResponse = api1.retrofitService.getQuestions()

            val questionList = questionResponse.results

            questionList.forEach {questionApi->
              val questionDb = Question(0,
                  category = questionApi.category,
                  type = questionApi.type,
                  question = questionApi.question,
                  correct_answer = questionApi.correct_answer
              )
                val id =  database.mindMasterDao.insertQuestion(questionDb)


                questionApi.incorrect_answers.forEach {incorectAnswer->

                    val incorectAnswerDb = IncorrectAnswer(
                        questionId = id,
                        incorrectAnswer = incorectAnswer
                    )

                    database.mindMasterDao.insertIncorrectAnswer(incorectAnswerDb)


                }
            }




        } catch (e: Exception) {

            Log.e("QuestionApiservice", "Error:$e")
        }


    }
}