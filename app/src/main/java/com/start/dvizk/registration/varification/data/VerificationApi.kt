package com.start.dvizk.registration.varification.data

import com.start.dvizk.registration.registr.presentation.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface VerificationApi {

	@FormUrlEncoded
	@POST("/api/v2/user/verification")
	fun verify(
		@Field("email") email: String,
		@Field("verification_code") verificationCode: String
	): Call<User>
}