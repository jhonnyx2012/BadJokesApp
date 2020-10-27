package com.cornershopapp.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel<STATE, NAVIGATION>(initialState: STATE? = null)
    : ViewModel() {

    protected val state: MutableLiveData<STATE> = MutableLiveData()
    val stateLiveData = state.asLiveData()

    protected val navigation: MutableLiveData<NAVIGATION> = MutableLiveData()
    val navigationLiveData = state.asLiveData()


    init {
        initialState?.let { state.value = it }
    }
}