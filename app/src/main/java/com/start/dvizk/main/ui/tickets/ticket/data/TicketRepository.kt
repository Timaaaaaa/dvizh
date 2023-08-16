package com.start.dvizk.main.ui.tickets.ticket.data

import com.start.dvizk.main.ui.tickets.ticket.data.model.TicketDataModel
import com.start.dvizk.network.Response
import org.json.JSONObject

class TicketRepository(
	private val ticketApi: TicketApi
) {

	fun getTicketById(
		ticketId: Int,
		token: String
	): Response<TicketDataModel, String> {
		try {
			val response = ticketApi
				.getTicketById(
					ticketId = ticketId,
					token = "Bearer $token"
				)
				.execute()

			if (response.isSuccessful) {
				response.body()?.let { return Response.Success(it.ticket) }
			}
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")

			return Response.Error(message)
		} catch (ex: Exception) {
			return Response.Error(ex.localizedMessage?.toString() ?: "")
		}
	}

	fun cancelTicket(
		ticketId: Int,
		token: String,
		ticket_cancel_reason_id: Int,
		rating: Int,
		review: String
	): Response<String, String> {
		val response = ticketApi
			.cancelTicket(
				ticketId = ticketId,
				token = "Bearer $token",
				ticket_cancel_reason_id = ticket_cancel_reason_id,
				rating = rating,
				review = review
			)
			.execute()

		if (response.isSuccessful) {
			val message = JSONObject(response.body().toString()).getString("message")
			return Response.Success(message)
		}

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}