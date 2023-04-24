package com.start.dvizk.network

/**
 * @author marshal@kolesa.kz
 */
class DefaultApiErrorExceptionFactory(): ApiErrorExceptionFactory {

    override fun createException(
            httpStatusCode: Int,
            message: String?
    ): Exception = Exception(message)
}