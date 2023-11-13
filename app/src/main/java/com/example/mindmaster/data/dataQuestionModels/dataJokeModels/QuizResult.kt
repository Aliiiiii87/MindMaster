package com.example.mindmaster.data.dataQuestionModels.dataJokeModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mikephil.charting.data.BarEntry

@Entity
data class QuizResult(

    @PrimaryKey(autoGenerate = true)

    val id: Long,

    val category: String,
    val difficulty: String,
    val score: Int,



)
