package com.example.mindmaster.remote

import com.example.mindmaster.data.Question
import com.example.mindmaster.data.dataQuestionModels.QuestionResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL1 = "https://opentdb.com/"


// Moshi konvertiert Serverantworten in Kotlin Objekte

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


// Retrofit übernimmt die Kommunikation und übersetzt die Antwort

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL1)
    .build()


interface MindMasterApiService{


    @GET("api.php?amount=5&type=multiple")
    suspend fun getQuestions():QuestionResponse

    @GET("api.php?amount=20&type=multiple")
    suspend fun getQuestionsLevel(@Query("difficulty")difficulty : String,
                                   @Query("category")category : Int): QuestionResponse



}


object MindMasterApi{

    val retrofitService : MindMasterApiService by lazy{ retrofit.create(MindMasterApiService::class.java)}
}