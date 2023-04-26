package com.start.dvizk.registration.createpassword.domain

import com.google.gson.JsonObject
import com.start.dvizk.network.ApiErrorExceptionFactory
import com.start.dvizk.network.Response
import com.start.dvizk.registration.registr.data.RegistrationApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class PasswordGenerationRepository(
	private val registrationApi: RegistrationApi,
	private val apiErrorExceptionFactory: ApiErrorExceptionFactory
) {
	fun registr(
		email: String,
		password: String,
		name: String,
		nickname: String,
		phone_number: String,
		gender: String,
		birthday: String,
		image: MultipartBody.Part?,
	): Response<JsonObject, String> {
		val response = registrationApi
			.registr(
				email = email.toRequestBody(MultipartBody.FORM),
				password = password.toRequestBody(MultipartBody.FORM),
				name = name.toRequestBody(MultipartBody.FORM),
				nickname = nickname.toRequestBody(MultipartBody.FORM),
				phone_number = phone_number.toRequestBody(MultipartBody.FORM),
				gender = gender.toRequestBody(MultipartBody.FORM),
				birthday = birthday.toRequestBody(MultipartBody.FORM),
				image = image,
			)
			.execute()

		if(response.isSuccessful) return Response.Success(response.body()!!)

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}