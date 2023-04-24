package com.start.dvizk.registration.registr.presentation

import com.google.gson.JsonObject

sealed class RegistrationState {

    object Loading : RegistrationState()

    data class Failed(
        val message: String
    ) : RegistrationState()

    data class Success(
            val value: JsonObject
    ) : RegistrationState()
}