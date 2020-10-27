package com.cornershopapp.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cornershopapp.core.data.error.APIError
import com.cornershopapp.core.data.error.RequestErrorHelper
import com.cornershopapp.core.domain.ResourceRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Creates a [CoroutineExceptionHandler] instance that
 * translates the throwable to an [APIError] and changes the ViewModel
 * state to the indicated error state
 */
@Suppress("FunctionName")
inline fun <reified T : Any> BaseViewModel<*, *>.withHandler(resourceRepository: ResourceRepository): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            val error = RequestErrorHelper.getError(exception, resourceRepository)
            (stateLiveData as MutableLiveData).value = T::class.java.getConstructor(String::class.java).newInstance(error.errorMessage)
        }
    }

/**
 * Simplify launching a coroutine with our custom error handler
 */
inline fun <reified T : Any> BaseViewModel<*, *>.launchSafe(resourceRepository: ResourceRepository,
                                                         noinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(withHandler<T>(resourceRepository), CoroutineStart.DEFAULT, block)
}