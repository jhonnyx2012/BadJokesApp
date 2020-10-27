package com.cornershopapp.core.logging

import timber.log.Timber

object LogcatLogger {
    @JvmStatic
    fun initialize() {
        Timber.plant(Timber.DebugTree())
    }

    @JvmStatic
    fun logError(message: String) {
        Timber.e(message)
    }

    @JvmStatic
    fun logWarning(tag: String, e: Throwable) {
        Timber.tag(tag).w(e)
    }

    @JvmStatic
    fun logException(e: Throwable) {
        Timber.e(e)
    }

    @JvmStatic
    fun logException(e: Throwable, message: String) {
        Timber.e(e, message)
    }

    @JvmStatic
    fun logVerbose(message: String) {
        Timber.v(message)
    }

    @JvmStatic
    fun logDebug(message: String) {
        Timber.d(message)
    }
}