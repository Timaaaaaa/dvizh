package com.start.dvizk.main.ui.detail.data

import com.start.dvizk.main.ui.detail.data.model.CancellationRulesDataModel
import com.start.dvizk.main.ui.detail.data.model.EventDetailDataModel
import com.start.dvizk.main.ui.detail.data.model.EventRulesDataModel
import com.start.dvizk.network.Response
import org.json.JSONObject

class EventDetailRepository(
	private val eventDetailApi: EventDetailApi
) {

	fun getEventDetails(
		eventId: Int
	): Response<EventDetailDataModel, String> {
		try {
			val response = eventDetailApi
				.getEventDetails(
					event_id = eventId
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

	fun getCancellationRules(
		eventId: Int
	): Response<CancellationRulesDataModel, String> {
		try {
			val response = eventDetailApi
				.getCancellationRules(
					event_id = eventId
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

	fun getEventRules(
		eventId: Int
	): Response<EventRulesDataModel, String> {
		try {
			val response = eventDetailApi
				.getEventRules(
					event_id = eventId
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
}