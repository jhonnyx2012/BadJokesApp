package com.cornershopapp.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cornershopapp.core.data.error.APIError
import com.cornershopapp.core.data.error.RequestErrorHelper
import com.cornershopapp.core.domain.ResourceRepository
import com.cornershopapp.core.logging.LogcatLogger
import kotlinx.coroutines.*
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Simplify launching a coroutine and avoid crashes caused by errors
 */
fun BaseViewModel<*, *>.launchSafe(dispatcher : CoroutineDispatcher, block: suspend CoroutineScope.() -> Unit) {
    try {
        viewModelScope.launch(dispatcher, CoroutineStart.DEFAULT, block)
    } catch (throwable: Throwable) {
        LogcatLogger.logException(throwable, "while executing a coroutine with launchSafe")
    }
}

/**
 * Creates a [CoroutineExceptionHandler] instance that
 * translates the throwable to an [APIError] and changes the ViewModel
 * state to the indicated error state
 */
@Suppress("FunctionName")
inline fun <reified T : Any> BaseViewModel<*, *>.withHandler(resources: ResourceRepository,
                                                             crossinline errorStateCreator : (message : String) -> T)
        : CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            LogcatLogger.logException(exception, "while executing a coroutine with launchSafe")
            val error = RequestErrorHelper.getError(exception, resources)
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    (stateLiveData as MutableLiveData).value = errorStateCreator.invoke(error.errorMessage)
                }
            }
        }
    }

/**
 * Simplify launching a coroutine with our custom error handler and the indicated dispatcher
 */
inline fun <reified T : Any> BaseViewModel<*, *>.launchSafe(dispatcher : CoroutineDispatcher,
                                                            resources: ResourceRepository,
                                                            crossinline errorStateCreator : (message : String) -> T,
                                                            noinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(dispatcher + withHandler(resources, errorStateCreator), CoroutineStart.DEFAULT, block)
}