package com.start.dvizk.registration.registr.domain

import com.google.gson.JsonObject
import com.start.dvizk.network.ApiErrorExceptionFactory
import com.start.dvizk.network.Response
import com.start.dvizk.network.asNewResponse
import com.start.dvizk.registration.registr.data.VerificationApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class VerificationRepository(
	private val verificationApi: VerificationApi,
	private val apiErrorExceptionFactory: ApiErrorExceptionFactory
) {
	fun verify(
		email: String,
		verificationCode: String
	): Response<JsonObject, Exception> {
		val response = verificationApi
			.verify(
				email =   email,
				verificationCode =  verificationCode
			)
			.execute()
			.asNewResponse(apiErrorExceptionFactory)


		if (response is Response.Error) return response

		response as Response.Success
		return Response.Success(response.result)
	}
}