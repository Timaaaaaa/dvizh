package com.start.dvizk.create.steps.language

import com.start.dvizk.create.steps.language.model.EventLanguage
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.main.ui.home.presentation.model.EventResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface LanguagesListApi {

	@GET("/api/v2/language")
	fun getLanguages(): Call<MutableList<EventLanguage>>
}