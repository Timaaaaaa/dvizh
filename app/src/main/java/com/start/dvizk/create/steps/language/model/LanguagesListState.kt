package com.start.dvizk.create.steps.language.model

import com.google.gson.JsonObject

sealed class LanguagesListState {

    object Loading : LanguagesListState()

    data class Failed(
        val message: String
    ) : LanguagesListState()

    data class Success(
            val categories: List<EventLanguage>
    ) : LanguagesListState()
}