package com.start.dvizk.create.steps.data.model

sealed class RequestResponseState {

    object Loading: RequestResponseState()

    data class Failed(
        val message: String
    ): RequestResponseState()

    data class Success(
        val value: Any
    ): RequestResponseState()

}