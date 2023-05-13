package com.start.dvizk.create.organization.create.data

import com.start.dvizk.network.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CreateOrganizationRepository(
	private val createOrganizationApi: CreateOrganizationApi
) {

	fun createOrganization(
		token: String,
		name: String,
		description: String,
		phone_number: String,
		instagram: String,
		whatsapp: String?,
		email: String?,
		image: MultipartBody.Part?,
	): Response<String, String> {
		val response = createOrganizationApi
			.createOrganization(
				token = "Bearer $token",
				name = name.toRequestBody(MultipartBody.FORM),
				description = description.toRequestBody(MultipartBody.FORM),
				phone_number = phone_number.toRequestBody(MultipartBody.FORM),
				instagram = instagram.toRequestBody(MultipartBody.FORM),
				whatsapp = whatsapp?.toRequestBody(MultipartBody.FORM),
				email = email?.toRequestBody(MultipartBody.FORM),
				image = image,
			)
			.execute()


		if (response.code() == 200) {
			val message = JSONObject(response.body().toString()).getString("message")
			return Response.Success(message)
		}

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}