package com.example.mindmaster.remote

import com.example.mindmaster.data.dataQuestionModels.dataJokeModels.Joke
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val  BASE_URL2 = "https://v2.jokeapi.dev/"


// Moshi konvertiert Serverantworten in Kotlin Objekte
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


// Retrofit übernimmt die Kommunikation und übersetzt die Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL2)
    .build()


interface JokeApiService{

    @GET("joke/Any?lang=de&blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=single")
     suspend fun getJokes(): Joke
}

object JokeApi{

    val retrofitService : JokeApiService by lazy{ retrofit.create(JokeApiService::class.java)}
}
