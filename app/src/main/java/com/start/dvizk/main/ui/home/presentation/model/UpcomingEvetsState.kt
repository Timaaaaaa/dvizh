package com.start.dvizk.main.ui.home.presentation.model

import com.google.gson.JsonObject

sealed class UpcomingEvetsState {

    object Loading : UpcomingEvetsState()

    data class Failed(
        val message: String
    ) : UpcomingEvetsState()

    data class Success(
            val events: List<Event>
    ) : UpcomingEvetsState()
}