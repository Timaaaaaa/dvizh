package com.start.dvizk.network

/**
 * @author marshal@kolesa.kz
 */
interface ApiErrorExceptionFactory {

    fun createException(
            httpStatusCode: Int,
            message: String? = null
    ): Exception
}