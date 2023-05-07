package com.start.dvizk.create.organization.list.data

import com.start.dvizk.create.organization.list.presentation.model.OrganizationList
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.main.ui.home.presentation.model.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OrganizationListApi {

	@GET("/api/v2/organization")
	fun getOrganizationList(
		@Query("user_id") user_id: Int
	): Call<OrganizationList>

}