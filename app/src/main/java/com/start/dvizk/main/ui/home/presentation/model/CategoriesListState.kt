package com.start.dvizk.main.ui.home.presentation.model

import com.google.gson.JsonObject

sealed class CategoriesListState {

    object Loading : CategoriesListState()

    data class Failed(
        val message: String
    ) : CategoriesListState()

    data class Success(
            val categories: List<Category>
    ) : CategoriesListState()
}