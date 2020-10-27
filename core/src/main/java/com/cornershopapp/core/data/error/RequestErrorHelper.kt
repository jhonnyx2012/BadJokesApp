package com.cornershopapp.core.data.error

import com.cornershopapp.core.logging.LogcatLogger.logException
import com.cornershopapp.core.domain.ResourceRepository
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

object RequestErrorHelper {
    fun getError(e: Throwable, resources: ResourceRepository): APIError {
        logException(e)
        return when {
            isRequestError(e) ->
                try {
                    parseError((e as HttpException).response(), resources.defaultErrorMessageString)
                } catch (ignored: Exception) {
                    parseError((e as HttpException).response(), resources.defaultErrorMessageString)
                }
            isConnectionError(e) -> parseError(resources.connectionErrorString)
            else -> parseError(e.localizedMessage.orEmpty())
        }
    }

    private fun isConnectionError(e: Throwable): Boolean {
        return e is SocketTimeoutException || e is IOException || e.message != null && e.message!!.contains(
            "UnknownHostException"
        )
    }

    private fun isRequestError(e: Throwable?): Boolean {
        return e is HttpException || e is HttpException
    }

    private fun parseError(message: String): APIError {
        return APIError(message)
    }

    private fun parseError(response: Response<*>?, defaultError: String): APIError {
        return APIError(response!!, defaultError)
    }
}