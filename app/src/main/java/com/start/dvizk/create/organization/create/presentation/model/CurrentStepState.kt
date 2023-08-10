package com.start.dvizk.create.organization.create.presentation.model

import com.google.gson.JsonObject
import com.start.dvizk.create.organization.list.presentation.model.CurrentStep

sealed class CurrentStepState {

    object Loading : CurrentStepState()

    data class Failed(
        val message: String
    ) : CurrentStepState()

    data class Success(
            val step: CurrentStep
    ) : CurrentStepState()
}