package com.start.dvizk.create.organization.create.data

import com.google.gson.JsonObject
import com.start.dvizk.main.ui.home.presentation.model.EventResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CreateOrganizationApi {

	@Multipart
	@POST("/api/v2/organization")
	fun createOrganization(
		@Header("Authorization") token: String,
		@Part("name") name: RequestBody,
		@Part("description") description: RequestBody,
		@Part("phone_number") phone_number: RequestBody,
		@Part("instagram") instagram: RequestBody,
		@Part("whatsapp") whatsapp: RequestBody?,
		@Part("email") email: RequestBody?,
		@Part image: MultipartBody.Part?,
	): Call<JsonObject>
}