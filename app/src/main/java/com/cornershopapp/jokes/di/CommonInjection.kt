package com.cornershopapp.jokes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornershopapp.core.data.AndroidResourceRepository
import com.cornershopapp.jokes.data.datasource.remote.JokesRemoteDataSource
import com.cornershopapp.jokes.data.datasource.remote.JokesApiDataSource
import com.cornershopapp.jokes.data.network.JokesApi
import com.cornershopapp.jokes.domain.repository.JokesRepository
import com.cornershopapp.core.domain.ResourceRepository
import com.cornershopapp.jokes.data.datasource.repository.JokesRepositoryImp
import com.cornershopapp.jokes.domain.usecase.GetRandomJokeUseCase
import com.cornershopapp.jokes.presentation.JokesViewModel
import kotlinx.coroutines.Dispatchers

object CommonInjection : ViewModelProvider.Factory  {
    private fun provideGetRandomJokeUseCase() : GetRandomJokeUseCase {
        return GetRandomJokeUseCase(provideJokesRepository())
    }

    private fun provideJokesRepository(): JokesRepository {
        return JokesRepositoryImp(provideJokesRemoteDataSource())
    }

    private fun provideJokesRemoteDataSource(): JokesRemoteDataSource {
        return JokesApiDataSource(provideJokesApiService())
    }

    private fun provideJokesApiService() = JokesApi.service

    fun provideResourcesRepository(): ResourceRepository {
        return AndroidResourceRepository
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokesViewModel::class.java) -> JokesViewModel(
                provideGetRandomJokeUseCase(),
                provideResourcesRepository(),
                Dispatchers.IO) as T
            else -> throw Throwable("unknown ViewModel class $modelClass")
        }
    }
}