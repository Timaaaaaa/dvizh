package com.start.dvizk.network

sealed class Response<out Result, out Error> {

    data class Success<Result>(
            val result: Result
    ) : Response<Result, Nothing>()

    data class Error<Error>(
            val error: Error
    ) : Response<Nothing, Error>()

    companion object {
        val SuccessUnit = Success(Unit)
        val ErrorUnit = Error(Unit)
    }
}

fun <Result> Response<Result, Any>.getOrNull(): Result? = when (this) {
    is Response.Success -> this.result
    is Response.Error -> null
}

/**
 * Обязательно проверять на ошибку перед использованием
 *
 * ```
 * if (apiResponse is Response.Error) return apiResponse
 * ```
 */
fun <Result> Response<Result, Any>.getOrThrow(): Result = when (this) {
    is Response.Success -> this.result
    is Response.Error -> throw IllegalStateException("Response is not success")
}

inline fun <Result> Response<Result, Any>.getOrElse(
        elseFun: () -> Result
): Result = when (this) {
    is Response.Success -> this.result
    is Response.Error -> elseFun.invoke()
}

/**
 * Возвращает результат в случае успеха, либо пустой список в случае ошибки
 */
fun <Result: List<E>, E> Response<Result, Any>.getOrEmpty(): Result {
    return when (this) {
        is Response.Success -> this.result
        is Response.Error -> emptyList<E>() as Result
    }
}

inline fun <Result1, Result2, Error> Response<Result1, Error>.mapIfSuccess(
        mapFunc: (Result1) -> Result2
): Response<Result2, Error> = when (this) {
    is Response.Success -> Response.Success(mapFunc(this.result))
    is Response.Error -> this
}

inline fun <Result1, Result2, Error: Exception> Response<Result1, Error>.mapIfSuccessSafely(
        mapFunc: (Result1) -> Result2
): Response<Result2, Error> = when (this) {
    is Response.Success -> {
        try {
            Response.Success(mapFunc(this.result))
        } catch (ex: Exception) {
            Response.Error(ex as Error)
        }
    }
    is Response.Error ->
        this
}

fun <Result> Response<Result, Nothing>.get(): Result = when (this) {
    is Response.Success -> this.result
    is Response.Error -> throw IllegalStateException("Nothing will never be invoked")
}

inline fun <Result> runSafely(
        funBody: () -> Result
): Response<Result, Exception> = try {
    Response.Success(
            result = funBody.invoke()
    )
} catch (exception: Exception) {
    Response.Error(
            error = exception
    )
}

inline fun <Result, Error> Response<Result, Error>.unpack(
        doOnSuccess: (Result) -> Unit,
        doOnError: (Error) -> Unit
) {
    when (this) {
        is Response.Success -> doOnSuccess.invoke(result)
        is Response.Error -> doOnError.invoke(error)
    }
}

inline fun <Result, Error, Return> Response<Result, Error>.unpackResult(
        doOnSuccess: (Result) -> Return,
        doOnError: (Error) -> Return
): Return = when (this) {
    is Response.Success -> doOnSuccess.invoke(result)
    is Response.Error -> doOnError.invoke(error)
}

/**
 * Фильтрует успешные ответы и распаковывае их
 */
fun <Result, Error> Iterable<Response<Result, Error>>.filterSuccess(): List<Result> {
    return this.filterIsInstance<Response.Success<Result>>().map { it.result }
}

/**
 * Фильтрует ошибочные ответы и распаковывае их
 */
fun <Result, Error> Iterable<Response<Result, Error>>.filterError(): List<Error> {
    return this.filterIsInstance<Response.Error<Error>>().map { it.error }
}

fun <T> retrofit2.Response<T>.asNewResponse(
    apiErrorExceptionFactory: ApiErrorExceptionFactory
): Response<T, Exception> = this.body()?.let { Response.Success(it) }
    ?: this.errorBody()?.let {
        val exception = apiErrorExceptionFactory.createException(
            httpStatusCode = code(),
            message = it.string()
        )
        Response.Error(exception)
    }
    ?: Response.Error(IllegalStateException("Could not receive response"))

fun <T> Response<retrofit2.Response<T>, Exception>.asNewResponse(
    apiErrorExceptionFactory: ApiErrorExceptionFactory
): Response<T, Exception> = when (this) {
    is Response.Success -> {
        this.result.asNewResponse(apiErrorExceptionFactory)
    }
    is Response.Error -> this
}