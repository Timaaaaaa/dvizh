package com.start.dvizk.create.steps.language

import com.start.dvizk.create.steps.language.model.EventParameter
import retrofit2.Call
import retrofit2.http.GET

interface LanguagesListApi {

	@GET("/api/v2/language")
	fun getLanguages(): Call<MutableList<EventParameter>>
}