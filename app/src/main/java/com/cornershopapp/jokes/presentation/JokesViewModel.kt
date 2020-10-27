package com.cornershopapp.jokes.presentation

import com.cornershopapp.core.domain.ResourceRepository
import com.cornershopapp.core.presentation.BaseViewModel
import com.cornershopapp.core.presentation.launchSafe
import com.cornershopapp.jokes.domain.model.Joke
import com.cornershopapp.jokes.domain.usecase.GetRandomJokeUseCase

sealed class JokesState {
    object Loading : JokesState()
    class Loaded(val content : Joke) : JokesState()
    class Error(val message: String) : JokesState()
}

sealed class JokesNavigation

class JokesViewModel(private val getRandomJokeUseCase: GetRandomJokeUseCase,
                     private val resourceRepository: ResourceRepository)
    : BaseViewModel<JokesState, JokesNavigation>(JokesState.Loading) {

    fun onStart() {
        loadRandomJoke()
    }

    fun onMoreButtonClicked() {
        loadRandomJoke()
    }

    private fun loadRandomJoke() {
        state.value = JokesState.Loading
        launchSafe<JokesState.Error>(resourceRepository) {
            state.value = JokesState.Loaded(getRandomJokeUseCase())
        }
    }
}