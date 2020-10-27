package com.cornershopapp.jokes.domain.usecase

import com.cornershopapp.jokes.domain.repository.JokesRepository

class GetRandomJokeUseCase(private val repository: JokesRepository) {
    suspend operator fun invoke() = repository.getRandomJoke()
}