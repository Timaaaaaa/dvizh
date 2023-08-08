package com.start.dvizk.main.ui.detail.data

import com.google.gson.JsonObject
import com.start.dvizk.main.ui.detail.data.model.CancellationRulesDataModel
import com.start.dvizk.main.ui.detail.data.model.EventDetailDataModel
import com.start.dvizk.main.ui.detail.data.model.EventRulesDataModel
import com.start.dvizk.registration.registr.presentation.model.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface EventDetailApi {

	@GET("/api/v2/event/{event_id}")
	fun getEventDetails(
		@Path("event_id") event_id: Int
	): Call<EventDetailDataModel>

	@GET("/api/v3/event/{event_id}/cancellation_rules")
	fun getCancellationRules(
		@Path("event_id") event_id: Int
	): Call<CancellationRulesDataModel>

	@GET("/api/v3/event/{event_id}/rules")
	fun getEventRules(
		@Path("event_id") event_id: Int
	): Call<EventRulesDataModel>

	@FormUrlEncoded
	@POST("/api/v2/order/step/first")
	fun orderFirstStep(
		@Header("Authorization") authorization: String,
		@Field("datetimeId") datetimeId: Int,
	): Call<JsonObject>
}