package com.example.mindmaster.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.IncorrectAnswer

@Entity
data class Question(

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val category: String,
    val type: String,
    val question : String,
    val difficulty : String,
    val correct_answer : String,





    )
