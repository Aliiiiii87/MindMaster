package com.example.mindmaster.data.dataQuestionModels.dataJokeModels

data class QuestionApi(
    val category: String,
    val type: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>

    )
