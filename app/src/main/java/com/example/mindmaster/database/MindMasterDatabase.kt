package com.example.mindmaster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.IncorrectAnswer
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.Joke
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuizResult

@Database(entities = [Question::class,Joke::class,IncorrectAnswer::class,QuizResult::class],version =1)
abstract class  MindMasterDatabase : RoomDatabase(){
    
    abstract val mindMasterDao : MindMasterDao
}


private lateinit var INSTANCE : MindMasterDatabase

fun getInstance(context: Context): MindMasterDatabase {
    synchronized(MindMasterDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MindMasterDatabase::class.java,
                "mindMaster_database"
            ).build()
        }
        return INSTANCE
    }
}