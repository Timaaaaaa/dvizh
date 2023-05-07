package com.start.dvizk.create.organization.list.presentation.model

import com.google.gson.JsonObject

sealed class OrganizationListState {

    object Loading : OrganizationListState()

    data class Failed(
        val message: String
    ) : OrganizationListState()

    data class Success(
            val organizations: MutableList<Organization>
    ) : OrganizationListState()
}