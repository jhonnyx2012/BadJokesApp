package com.cornershopapp.core.data.error

import retrofit2.Response
import java.net.HttpURLConnection

class APIError {
    var statusCode = 0
        private set
    var statusError: String
        private set
    private val mustUpdateApp = false
    private lateinit var defaultError: String

    constructor(response: Response<*>, defaultError: String) {
        statusCode = response.code()
        statusError = response.message()
        this.defaultError = defaultError
    }

    constructor(response: Response<*>) {
        statusCode = response.code()
        statusError = response.message()
        defaultError = ""
    }

    constructor(message: String) {
        statusError = message
        defaultError = ""
    }

    val errorMessage: String
        get() = when {
            defaultError.isNotEmpty() -> defaultError
            statusError.isNotEmpty() -> statusError
            else -> statusCode.toString()
        }

    val isConnectionError: Boolean
        get() = statusCode == 0

    fun mustUpdateApp(): Boolean {
        return mustUpdateApp
    }

    val isGone: Boolean
        get() = statusCode == HttpURLConnection.HTTP_GONE
}