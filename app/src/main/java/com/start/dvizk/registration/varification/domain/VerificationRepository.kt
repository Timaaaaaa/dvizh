package com.start.dvizk.registration.registr.domain

import com.start.dvizk.network.ApiErrorExceptionFactory
import com.start.dvizk.network.Response
import com.start.dvizk.network.asNewResponse
import com.start.dvizk.registration.varification.data.VerificationApi
import com.start.dvizk.registration.registr.presentation.model.User

class VerificationRepository(
	private val verificationApi: VerificationApi,
	private val apiErrorExceptionFactory: ApiErrorExceptionFactory
) {
	fun verify(
		email: String,
		verificationCode: String
	): Response<User, Exception> {
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