package com.start.dvizk.create.organization.list.data

import com.start.dvizk.create.organization.list.presentation.model.CurrentStep
import com.start.dvizk.create.organization.list.presentation.model.OrganizationList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OrganizationApi {

	@GET("/api/v2/organization")
	fun getOrganizationList(
		@Query("user_id") user_id: Int
	): Call<OrganizationList>


	@GET("/api/v3/ticket/event/current_step")
	fun getCurrentStep(
		@Header("Authorization") token: String,
		@Query("organization_id") organization_id: Int
	): Call<CurrentStep>

}