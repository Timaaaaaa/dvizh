package com.start.dvizk.main.ui.detail.data

import com.start.dvizk.main.ui.detail.data.model.CancellationRules
import com.start.dvizk.network.Response
import org.json.JSONObject

class EventDetailRepository(
	private val eventDetailApi: EventDetailApi
) {

	fun getCancellationRules(
		event_id: Int
	): Response<CancellationRules, String> {
		try {
			val response = eventDetailApi
				.getCancellationRules(
					event_id = event_id
				)
				.execute()

			if (response.isSuccessful) {
				response.body()?.let { return Response.Success(it) }
			}
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")

			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage?.toString() ?: "")
		}
	}
}