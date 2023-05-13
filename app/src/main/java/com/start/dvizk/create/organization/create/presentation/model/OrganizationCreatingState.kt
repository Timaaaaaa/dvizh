package com.start.dvizk.create.organization.create.presentation.model

import com.google.gson.JsonObject

sealed class OrganizationCreatingState {

    object Loading : OrganizationCreatingState()

    data class Failed(
        val message: String
    ) : OrganizationCreatingState()

    data class Success(
            val message: String
    ) : OrganizationCreatingState()
}