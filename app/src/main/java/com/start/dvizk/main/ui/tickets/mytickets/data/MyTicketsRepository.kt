package com.start.dvizk.main.ui.tickets.mytickets.data

import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket
import com.start.dvizk.main.ui.tickets.mytickets.data.model.response.UpcomingTicketsResponse
import com.start.dvizk.network.Response
import org.json.JSONObject

class MyTicketsRepository(
	private val myTicketsApi: MyTicketsApi
) {

	fun getUserUpcomingTickets(
		page: Int,
		token: String
	): Response<UpcomingTicketsResponse, String> {
		try {
			val response = myTicketsApi
				.getUserUpcomingTickets(
					page = page,
					token = "Bearer $token"
				)
				.execute()

			if (response.isSuccessful) {
				response.body()?.let { return Response.Success(it) }
			}
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")

			return Response.Error(message)
		} catch (ex: Exception) {
			return Response.Error(ex.localizedMessage?.toString() ?: "")
		}
	}

	fun getUserFinishedTickets(
		page: Int,
		token: String
	): Response<MutableList<MyTicket>, String> {
		try {
			val response = myTicketsApi
				.getUserFinishedTickets(
					page = page,
					token = "Bearer $token"
				)
				.execute()

			if (response.isSuccessful) {
				response.body()?.let { return Response.Success(it.tickets) }
			}
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")

			return Response.Error(message)
		} catch (ex: Exception) {
			return Response.Error(ex.localizedMessage?.toString() ?: "")
		}
	}

	fun getUserCancelledTickets(
		page: Int,
		token: String
	): Response<MutableList<MyTicket>, String> {
		try {
			val response = myTicketsApi
				.getUserCanceledTickets(
					page = page,
					token = "Bearer $token"
				)
				.execute()

			if (response.isSuccessful) {
				response.body()?.let { return Response.Success(it.tickets) }
			}
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")

			return Response.Error(message)
		} catch (ex: Exception) {
			return Response.Error(ex.localizedMessage?.toString() ?: "")
		}
	}
}