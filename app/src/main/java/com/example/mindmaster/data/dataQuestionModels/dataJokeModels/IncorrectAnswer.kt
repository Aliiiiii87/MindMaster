package com.example.mindmaster.data.dataQuestionModels.dataJokeModels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(primaryKeys = ["questionId","incorrectAnswer"])

data class IncorrectAnswer(



    // Das ist die ID zur Frage die zur Antwort gehört

    val questionId: Long  ,
    val incorrectAnswer: String
    )
