package com.start.dvizk.network

interface ApiErrorExceptionFactory {

    fun createException(
            httpStatusCode: Int,
            message: String? = null
    ): Exception
}
