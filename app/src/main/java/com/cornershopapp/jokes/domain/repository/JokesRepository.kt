package com.cornershopapp.jokes.domain.repository

import com.cornershopapp.jokes.domain.model.Joke

interface JokesRepository {
    suspend fun getRandomJoke() : Joke
}