package com.start.dvizk.registration.createpassword.presentation

import com.google.gson.JsonObject

sealed class PasswordGenerationState {

    object Loading : PasswordGenerationState()

    data class Failed(
        val message: String
    ) : PasswordGenerationState()

    data class Success(
            val value: JsonObject
    ) : PasswordGenerationState()
}