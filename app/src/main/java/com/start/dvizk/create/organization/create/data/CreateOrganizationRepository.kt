package com.start.dvizk.create.organization.create.data

import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.network.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CreateOrganizationRepository(
	private val createOrganizationApi: CreateOrganizationApi
) {

	fun getPopularEvents(
		token: String,
		name: String,
		description: String,
		phone_number: String,
		instagram: String,
		whatsapp: String,
		email: String,
		image: MultipartBody.Part?,
	): Response<List<Event>, String> {
		val response = createOrganizationApi
			.createOrganization(
				token = token.toRequestBody(MultipartBody.FORM),
				name = name.toRequestBody(MultipartBody.FORM),
				description = description.toRequestBody(MultipartBody.FORM),
				phone_number = phone_number.toRequestBody(MultipartBody.FORM),
				instagram = instagram.toRequestBody(MultipartBody.FORM),
				whatsapp = whatsapp.toRequestBody(MultipartBody.FORM),
				email = email.toRequestBody(MultipartBody.FORM),
				image = image,
			)
			.execute()

		if (response.isSuccessful) return Response.Success(response.body()!!.events)

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}