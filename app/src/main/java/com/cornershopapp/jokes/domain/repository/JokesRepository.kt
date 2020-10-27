package com.cornershopapp.jokes.domain.repository

import com.cornershopapp.jokes.domain.model.Joke
import com.cornershopapp.jokes.data.datasource.remote.JokesRemoteDataSource
import com.cornershopapp.jokes.data.mapper.toJoke

class JokesRepository(private val remote : JokesRemoteDataSource) {
    suspend fun getRandomJoke() : Joke {
        return remote.getRandomJoke().toJoke()
    }
}