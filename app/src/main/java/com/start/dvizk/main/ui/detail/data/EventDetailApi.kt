package com.start.dvizk.main.ui.detail.data

import com.start.dvizk.create.organization.list.presentation.model.OrganizationList
import com.start.dvizk.main.ui.detail.data.model.CancellationRules
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventDetailApi {

	@GET("/api/v3/event/{event_id}/cancellation_rules")
	fun getCancellationRules(
	@Path("event_id") event_id: Int
	): Call<CancellationRules>

}