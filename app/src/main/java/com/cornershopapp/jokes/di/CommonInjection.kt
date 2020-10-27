package com.cornershopapp.jokes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornershopapp.core.data.DeviceResourceDataSource
import com.cornershopapp.core.data.DeviceResourceDataSourceImp
import com.cornershopapp.jokes.data.datasource.remote.JokesRemoteDataSource
import com.cornershopapp.jokes.data.datasource.remote.JokesRemoteDataSourceImp
import com.cornershopapp.jokes.data.network.JokesApi
import com.cornershopapp.jokes.domain.repository.JokesRepository
import com.cornershopapp.core.domain.ResourceRepository
import com.cornershopapp.jokes.domain.usecase.GetRandomJokeUseCase
import com.cornershopapp.jokes.presentation.JokesViewModel

object CommonInjection : ViewModelProvider.Factory  {
    private fun provideGetRandomJokeUseCase() : GetRandomJokeUseCase {
        return GetRandomJokeUseCase(provideJokesRepository())
    }

    private fun provideJokesRepository(): JokesRepository {
        return JokesRepository(provideJokesRemoteDataSource())
    }

    private fun provideJokesRemoteDataSource(): JokesRemoteDataSource {
        return JokesRemoteDataSourceImp(provideJokesApiService())
    }

    private fun provideJokesApiService() = JokesApi.service

    fun provideResourcesRepository(): ResourceRepository {
        return ResourceRepository(provideDeviceResourceDataSource())
    }

    private fun provideDeviceResourceDataSource(): DeviceResourceDataSource {
        return DeviceResourceDataSourceImp
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokesViewModel::class.java) -> JokesViewModel(provideGetRandomJokeUseCase(), provideResourcesRepository()) as T
            else -> throw Throwable("unknown ViewModel class $modelClass")
        }
    }
}