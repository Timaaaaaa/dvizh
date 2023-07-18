package com.start.dvizk.main.ui.home.presentation.model

sealed class UpcomingEventsState {

    object Loading : UpcomingEventsState()

    data class Failed(
        val message: String
    ) : UpcomingEventsState()

    data class Success(
            val events: List<Event>
    ) : UpcomingEventsState()
}