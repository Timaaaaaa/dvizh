package com.start.dvizk.create.organization.list.data

import com.start.dvizk.create.organization.list.presentation.model.CurrentStep
import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.network.Response
import org.json.JSONObject

class OrganizationRepository(
	private val organizationApi: OrganizationApi
) {

	fun getOrganizationList(
		userId: Int
	): Response<MutableList<Organization>, String> {
		try {
			val response = organizationApi
				.getOrganizationList(
					user_id = userId
				)
				.execute()

			if (response.isSuccessful) {
				response.body()?.let { return Response.Success(it.data.toMutableList()) }
			}
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")

			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage?.toString() ?: "")
		}
	}

	fun getCurrentStep(
		token: String,
		organizationId: Int
	): Response<CurrentStep, String> {
		try {
			val response = organizationApi
				.getCurrentStep(
					token = "Bearer $token",
					organization_id = organizationId
				)
				.execute()

			if (response.isSuccessful) {
				response.body()?.let { return Response.Success(it) }
			}
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")

			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage.toString())
		}
	}
}