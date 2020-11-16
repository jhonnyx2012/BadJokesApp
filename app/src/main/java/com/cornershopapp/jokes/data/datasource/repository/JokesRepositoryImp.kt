package com.cornershopapp.jokes.data.datasource.repository

import com.cornershopapp.jokes.domain.model.Joke
import com.cornershopapp.jokes.data.datasource.remote.JokesRemoteDataSource
import com.cornershopapp.jokes.data.mapper.toJoke
import com.cornershopapp.jokes.domain.repository.JokesRepository

class JokesRepositoryImp(private val remote : JokesRemoteDataSource) : JokesRepository {
    override suspend fun getRandomJoke() : Joke {
        return remote.getRandomJoke().toJoke()
    }
}