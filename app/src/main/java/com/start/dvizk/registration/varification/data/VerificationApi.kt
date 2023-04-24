package com.start.dvizk.registration.registr.data

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface VerificationApi {

	@FormUrlEncoded
	@POST("/api/v2/user/verification")
	fun verify(
		@Field("email") email: String,
		@Field("verification_code") verificationCode: String
	): Call<JsonObject>
}