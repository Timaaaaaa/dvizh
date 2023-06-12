package com.start.dvizk.create.steps.data

import com.google.gson.JsonObject
import com.start.dvizk.network.ApiErrorExceptionFactory
import com.start.dvizk.network.Response
import com.start.dvizk.registration.registr.data.RegistrationApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class EventCreateRepository(
	private val eventCreateApi: EventCreateApi
) {
	fun sendEventType(
		token: String,
		numberStep: Int,
		type: String,
		eventId: Int,
	): Response<JsonObject, String> {
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
}