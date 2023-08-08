package com.start.dvizk.main.ui.tickets.mytickets.data

import com.start.dvizk.main.ui.tickets.mytickets.data.model.response.CanceledTicketsResponse
import com.start.dvizk.main.ui.tickets.mytickets.data.model.response.FinishedTicketsResponse
import com.start.dvizk.main.ui.tickets.mytickets.data.model.response.UpcomingTicketsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyTicketsApi {

	@GET("/api/v2/ticket/upcoming")
	fun getUserUpcomingTickets(
		@Query("page") page: Int,
		@Header("Authorization") token: String
	): Call<UpcomingTicketsResponse>

	@GET("/api/v2/ticket/past")
	fun getUserFinishedTickets(
		@Query("page") page: Int,
		@Header("Authorization") token: String
	): Call<FinishedTicketsResponse>

	@GET("/api/v2/ticket/rejected")
	fun getUserCanceledTickets(
		@Query("page") page: Int,
		@Header("Authorization") token: String
	): Call<CanceledTicketsResponse>

}