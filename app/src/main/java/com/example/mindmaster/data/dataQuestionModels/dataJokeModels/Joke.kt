package com.example.mindmaster.data.dataQuestionModels.dataJokeModels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Joke (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,

    val category : String,
    val type : String,
    val joke : String,



        )
