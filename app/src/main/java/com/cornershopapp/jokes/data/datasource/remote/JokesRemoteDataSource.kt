package com.cornershopapp.jokes.data.datasource.remote

import com.cornershopapp.jokes.data.dto.DataJoke
import com.cornershopapp.jokes.data.network.JokesApiService

interface JokesRemoteDataSource {
    suspend fun getRandomJoke() : DataJoke
}

class JokesRemoteDataSourceImp(private val service: JokesApiService) : JokesRemoteDataSource {

    override suspend fun getRandomJoke(): DataJoke {
        return service.getRandomJoke()
    }
}