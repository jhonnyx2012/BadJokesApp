package com.cornershopapp.core.presentation

import androidx.lifecycle.Observer

class OneTimeEvent<out T>(private val content: T) {
    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    @Suppress("unused")
    fun peekContent(): T = content
}

/**
 * An [Observer] for [OneTimeEvent]s, simplifying the pattern of checking if the [OneTimeEvent]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [OneTimeEvent]'s contents has not been handled.
 */
class OneTimeEventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<OneTimeEvent<T>> {
    override fun onChanged(event: OneTimeEvent<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}