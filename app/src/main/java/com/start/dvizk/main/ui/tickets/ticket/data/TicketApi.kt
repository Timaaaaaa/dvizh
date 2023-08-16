package com.start.dvizk.main.ui.tickets.ticket.data

import com.google.gson.JsonObject
import com.start.dvizk.main.ui.tickets.ticket.data.model.UserTicket
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TicketApi {

	@GET("/api/v2/ticket/{ticket_id}")
	fun getTicketById(
		@Path("ticket_id") ticketId: Int,
		@Header("Authorization") token: String
	): Call<UserTicket>

	@PUT("/api/v2/ticket/{ticket_id}/reject")
	fun cancelTicket(
		@Path("ticket_id") ticketId: Int,
		@Header("Authorization") token: String,
		@Query("ticket_cancel_reason_id") ticket_cancel_reason_id: Int,
		@Query("rating") rating: Int,
		@Query("review") review: String
	): Call<JsonObject>

}