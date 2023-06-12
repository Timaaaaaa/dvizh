package com.start.dvizk.create.steps.data

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface EventCreateApi {

	@FormUrlEncoded
	@POST("/api/v3/event/{number_step}")
	fun sendEventType(
		@Header("Authorization") token: String,
		@Path("number_step") numberStep: Int,
		@Field("type") type: String,
		@Field("event_id") eventId: Int,
	): Call<JsonObject>
}