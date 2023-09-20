package com.example.mindmaster.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.IncorrectAnswer
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.Joke
import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.QuestionWithIncorrectAnswers

@Dao
interface MindMasterDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionList(question:List<Question> )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncorrectAnswer(incorrectAnswer: IncorrectAnswer)

    @Query("SELECT *FROM question WHERE id = :id")
    fun getQuestion(id: Int):LiveData< Question>
    @Transaction
    @Query("SELECT * FROM question")
   fun getAllQuestion():LiveData<List<QuestionWithIncorrectAnswers>>

    @Query("SELECT*FROM question WHERE category= :category")
    fun getQuestionByCategory(category: String):List<Question>

    @Delete
   fun deleteQuestion(question: Question)

    @Query("DELETE FROM question")
   fun deleteAllQuestion()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertJoke(joke:Joke)

    @Query("SELECT*FROM joke")
    fun getAllJokes():LiveData<List<Joke>>


}