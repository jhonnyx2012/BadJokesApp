package com.cornershopapp.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel<STATE, NAVIGATION>(initialState: STATE? = null)
    : ViewModel() {

    protected val state: MutableLiveData<STATE> = MutableLiveData()
    val stateLiveData = state.asLiveData()

    protected val navigation: MutableLiveData<OneTimeEvent<NAVIGATION>> = MutableLiveData()
    val navigationLiveData = navigation.asLiveData()

    init {
        initialState?.let { state.value = it }
    }

    fun setState(newState : STATE) {
        state.postValue(newState)
    }

    fun navigateTo(newNavigation : NAVIGATION) {
        navigation.postValue(OneTimeEvent(newNavigation))
    }
}