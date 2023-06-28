package com.start.dvizk.create.steps.data

import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.language.model.EventLanguage
import com.start.dvizk.network.Response
import org.json.JSONObject

class EventCreateRepository(
	private val eventCreateApi: EventCreateApi
) {

	fun sendEventType(
		token: String,
		numberStep: Int,
		type: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventType(
				token = "Bearer $token",
				numberStep = numberStep,
				type = type,
				eventId = eventId,
			)
			.execute()


		if(response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}

	fun getLanguages(): Response<List<EventLanguage>, String> {
		val response = eventCreateApi
			.getLanguages()
			.execute()


		if(response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}