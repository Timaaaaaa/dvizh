package com.start.dvizk.main.ui.home.presentation.model

import com.google.gson.JsonObject

sealed class PopularEvetsState {

    object Loading : PopularEvetsState()

    data class Failed(
        val message: String
    ) : PopularEvetsState()

    data class Success(
            val events: List<Event>
    ) : PopularEvetsState()
}