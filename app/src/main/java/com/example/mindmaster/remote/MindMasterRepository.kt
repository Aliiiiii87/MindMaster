package com.example.mindmaster.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.QuestionResponse
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.IncorrectAnswer
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.Joke
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionApi
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult
import com.example.mindmaster.database.MindMasterDatabase


import kotlin.Exception

class MindMasterRepository(
    private val api1: MindMasterApi, private val api2: JokeApi,
    private var database: MindMasterDatabase
) {


    private val _question = MutableLiveData<List<QuestionWithIncorrectAnswers>>()
    val question: LiveData<List<QuestionWithIncorrectAnswers>>
        get() = _question


    private val _questionResult = database.mindMasterDao.getQuizResults()
    val questionResult: LiveData<List<QuizResult>>
        get() = _questionResult


    private val _joke = database.mindMasterDao.getAllJokes()
    val joke: LiveData<List<Joke>>
        get() = _joke


    val categories = database.mindMasterDao.getCategories()


    suspend fun getQuestionsLevelAndCategory() {


        if (database.mindMasterDao.getCount() == 0) {
            val categoryId = (9..24).toList()


            val difficulty = listOf<String>(

                "easy",
                "medium",
                "hard"
            )

            categoryId.forEach { id ->
                difficulty.forEach { difficulty ->

                    val questions = api1.retrofitService.getQuestionsLevel(difficulty, id)
                    saveQuestionsList(questions.results)

                }


            }


        }
    }


    fun insertResult(quizResult: QuizResult) {

        try {
            database.mindMasterDao.insertQuizResult(quizResult)
        } catch (e: Exception) {


        }


    }

    fun getCountResult(): Long {


        return database.mindMasterDao.getCountQuizResult()
    }


    private fun saveQuestionsList(questionList: List<QuestionApi>) {
        questionList.forEach { questionApi ->
            val questionDb = Question(
                0,
                category = questionApi.category,
                type = questionApi.type,
                question = questionApi.question,
                difficulty = questionApi.difficulty,
                correct_answer = questionApi.correct_answer

            )
            val id = database.mindMasterDao.insertQuestion(questionDb)


            questionApi.incorrect_answers.forEach { incorectAnswer ->

                val incorectAnswerDb = IncorrectAnswer(
                    questionId = id,
                    incorrectAnswer = incorectAnswer
                )

                database.mindMasterDao.insertIncorrectAnswer(incorectAnswerDb)


            }
        }


    }


    suspend fun getQuestionByCategory(category: String, difficulty: String) {

        val questionLevel = (database.mindMasterDao.getQuestionByCategory(category, difficulty))

        _question.postValue(questionLevel.take(20))


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

            questionList.forEach { questionApi ->
                val existingQuestion = database.mindMasterDao.getQuestionByCategory(
                    questionApi.category,
                    questionApi.difficulty
                )

                if (existingQuestion == null) {


                    val questionDb = Question(
                        0,
                        category = questionApi.category,
                        type = questionApi.type,
                        question = questionApi.question,
                        difficulty = questionApi.difficulty,
                        correct_answer = questionApi.correct_answer
                    )
                    val id = database.mindMasterDao.insertQuestion(questionDb)


                    questionApi.incorrect_answers.forEach { incorectAnswer ->

                        val incorectAnswerDb = IncorrectAnswer(
                            questionId = id,
                            incorrectAnswer = incorectAnswer
                        )

                        database.mindMasterDao.insertIncorrectAnswer(incorectAnswerDb)


                    }


                }


            }


        } catch (e: Exception) {

            Log.e("QuestionApiservice", "Error:$e")
        }


    }


}
