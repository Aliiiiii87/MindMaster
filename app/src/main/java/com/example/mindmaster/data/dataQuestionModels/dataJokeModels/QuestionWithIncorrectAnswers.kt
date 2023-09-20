package com.example.mindmaster.data.dataQuestionModels.dataJokeModels

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mindmaster.data.Question

data class QuestionWithIncorrectAnswers (

     @Embedded val question :Question,
     @Relation(
         parentColumn = "id",
         entityColumn = "questionId",
     )
     val incorrectAnswers: List<IncorrectAnswer>
         )






