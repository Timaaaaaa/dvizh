package com.start.dvizk.registration.registr.data

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RegistrationApi {

	@Multipart
	@POST("/api/v2/user/register")
	fun registr(
		@Part("email") email: RequestBody,
		@Part("password") password: RequestBody,
		@Part("name") name: RequestBody,
		@Part("nickname") nickname: RequestBody,
		@Part("phone_number") phone_number: RequestBody,
		@Part("gender") gender: RequestBody,
		@Part("birthday") birthday: RequestBody,
		@Part image: MultipartBody.Part?,
	): Call<JsonObject>
}