package com.start.dvizk.network

class DefaultApiErrorExceptionFactory(): ApiErrorExceptionFactory {

    override fun createException(
            httpStatusCode: Int,
            message: String?
    ): Exception = Exception(message)
}
