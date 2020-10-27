package com.cornershopapp.jokes.data.network

import com.cornershopapp.jokes.data.dto.DataJoke
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://sv443.net/jokeapi/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface JokesApiService {
    @GET("v2/joke/Any?type=twopart")
    suspend fun getRandomJoke() : DataJoke
}

object JokesApi {
    val service : JokesApiService by lazy {
        retrofit.create(JokesApiService::class.java)
    }
}