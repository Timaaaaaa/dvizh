package com.start.dvizk.create.organization.list.data

import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.network.ApiErrorExceptionFactory
import com.start.dvizk.network.Response
import org.json.JSONObject

class OrganizationListRepository(
	private val organizationListApi: OrganizationListApi
) {

	fun getPopularEvents(
		userId: Int
	): Response<MutableList<Organization>, String> {
		val response = organizationListApi
			.getOrganizationList(
				user_id = userId
			)
			.execute()

		if(response.isSuccessful) {
			 response.body()?.let { return Response.Success(it.data.toMutableList()) }
		}

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}