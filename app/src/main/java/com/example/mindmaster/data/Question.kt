package com.example.mindmaster.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Question(

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val category: String,
    val type: String,
    val question : String,
    val correct_answer : String,





    )
