package com.start.dvizk.create.steps.data

import com.start.dvizk.create.steps.calendar.model.EventDateTimeInterval
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.language.model.EventParameter
import com.start.dvizk.create.steps.photo.model.PhotoResponse
import com.start.dvizk.network.Response
import okhttp3.MultipartBody
import org.json.JSONObject

class EventCreateRepository(
	private val eventCreateApi: EventCreateApi
) {

	fun sendEventType(
		token: String,
		numberStep: Int,
		type: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventType(
				token = "Bearer $token",
				numberStep = numberStep,
				type = type,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventClassification(
		token: String,
		numberStep: Int,
		classification: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventClassification(
				token = "Bearer $token",
				numberStep = numberStep,
				classification = classification,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventEntryCondition(
		token: String,
		numberStep: Int,
		entry_condition: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventEntryCondition(
				token = "Bearer $token",
				numberStep = numberStep,
				entry_condition = entry_condition,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventMaxGroupSize(
		token: String,
		numberStep: Int,
		maximum_group_size: Int,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventMaxGroupSize(
				token = "Bearer $token",
				numberStep = numberStep,
				maximum_group_size = maximum_group_size,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun getLanguages(): Response<List<EventParameter>, String> {
		val response = eventCreateApi
			.getLanguages()
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}

	fun sendEventLanguages(
		token: String,
		languages: List<Int>,
		eventId: Int,
		numberStep: Int,
	): Response<StepDataApiResponse, String> {
		val authorization = "Bearer $token"

		val fieldMap = mutableMapOf<String, Any>()
		languages.forEachIndexed { index, language ->
			fieldMap["languages[$index]"] = language
		}

		try {
			val response = eventCreateApi
				.sendEventLanguages(
					eventId = eventId,
					fields = fieldMap,
					authorization = authorization,
					numberStep = numberStep
				)
				.execute()

			if (response.isSuccessful) return Response.Success(response.body()!!)
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")
			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage)
		}
	}

	fun sendEventCategory(
		token: String,
		catList: List<Int>,
		eventId: Int,
		numberStep: Int,
	): Response<StepDataApiResponse, String> {
		val authorization = "Bearer $token"

		val fieldMap = mutableMapOf<String, Any>()
		catList.forEachIndexed { index, cat ->
			fieldMap["categories[$index]"] = cat
		}

		try {
			val response = eventCreateApi
				.sendEventCategory(
					eventId = eventId,
					fields = fieldMap,
					authorization = authorization,
					numberStep = numberStep
				)
				.execute()

			if (response.isSuccessful) return Response.Success(response.body()!!)
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")
			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage)
		}
	}

	fun sendEventDescription(
		token: String,
		numberStep: Int,
		description: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventDescription(
				authorization = "Bearer $token",
				numberStep = numberStep,
				description = description,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun getCountry(): Response<List<EventParameter>, String> {
		val response = eventCreateApi
			.getCountry()
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}

	fun getCity(): Response<List<EventParameter>, String> {
		val response = eventCreateApi
			.getCity()
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}

	fun sendEventLocation(
		eventId: Int,
		country_id: Int,
		city_id: Int,
		apartment: Int,
		street: String,
		description: String,
		authorization: String,
		numberStep: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventLocation(
				eventId = eventId,
				country_id = country_id,
				city_id = city_id,
				apartment = apartment,
				street = street,
				description = description,
				authorization = "Bearer $authorization",
				numberStep = numberStep,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventDate(
		token: String,
		dateList: List<EventDateTimeInterval>,
		eventId: Int,
		numberStep: Int,
	): Response<StepDataApiResponse, String> {
		val authorization = "Bearer $token"

		val fieldMap = mutableMapOf<String, Any>()
		dateList.forEachIndexed { index, cat ->
			fieldMap["datetimes[$index][start]"] = cat.date + " " + cat.startTime + ":00"
			fieldMap["datetimes[$index][duration]"] = cat.duration.toString()
		}

		try {
			val response = eventCreateApi
				.sendEventDate(
					eventId = eventId,
					fields = fieldMap,
					authorization = authorization,
					numberStep = numberStep
				)
				.execute()

			if (response.isSuccessful) return Response.Success(response.body()!!)
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")
			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage)
		}
	}

	fun sendEventPurchaseDeadline(
		token: String,
		numberStep: Int,
		purchaseDeadline: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventPurchaseDeadline(
				authorization = "Bearer $token",
				numberStep = numberStep,
				purchase_deadline = purchaseDeadline,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventService(
		token: String,
		numberStep: Int,
		additional_services: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventService(
				authorization = "Bearer $token",
				numberStep = numberStep,
				additional_services = additional_services,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventNecessaryItems(
		token: String,
		catList: List<String>,
		eventId: Int,
		numberStep: Int,
	): Response<StepDataApiResponse, String> {
		val authorization = "Bearer $token"

		val fieldMap = mutableMapOf<String, Any>()
		catList.forEachIndexed { index, cat ->
			fieldMap["list[$index][name]"] = cat
		}

		try {
			val response = eventCreateApi
				.sendEventNecessaryItems(
					eventId = eventId,
					fields = fieldMap,
					authorization = authorization,
					numberStep = numberStep
				)
				.execute()

			if (response.isSuccessful) return Response.Success(response.body()!!)
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")
			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage)
		}
	}

	fun sendEventAllowedGuest(
		eventId: Int,
		age: Int,
		children: Int,
		additional_requirements: String,
		authorization: String,
		numberStep: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventAllowedGuest(
				age = age,
				children = children,
				additional_requirements = additional_requirements,
				eventId = eventId,
				authorization = "Bearer $authorization",
				numberStep = numberStep,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventName(
		token: String,
		numberStep: Int,
		name: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventName(
				authorization = "Bearer $token",
				numberStep = numberStep,
				name = name,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventCancelRule(
		token: String,
		numberStep: Int,
		rule: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventCancelRule(
				authorization = "Bearer $token",
				numberStep = numberStep,
				cancellation_rules = rule,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventRule(
		token: String,
		numberStep: Int,
		rule: String,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventRule(
				authorization = "Bearer $token",
				numberStep = numberStep,
				rules = rule,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendTeamCount(
		token: String,
		numberStep: Int,
		maximum_number_teams: Int,
		maximum_number_participants_team: Int,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendTeamCount(
				authorization = "Bearer $token",
				numberStep = numberStep,
				maximum_number_participants_team = maximum_number_participants_team,
				maximum_number_teams = maximum_number_teams,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventImage(
		token: String,
		numberStep: Int,
		eventId: Int,
	): Response<StepDataApiResponse, String> {
		val response = eventCreateApi
			.sendEventImage(
				authorization = "Bearer $token",
				numberStep = numberStep,
				eventId = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventMainImage(
		authorization: String,
		image: MultipartBody.Part?,
		eventId: Int,
	): Response<PhotoResponse, String> {
		val response = eventCreateApi
			.sendEventMainImage(
				authorization = "Bearer $authorization",
				image = image,
				event_id = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventAdditionalImage(
		authorization: String,
		image: MultipartBody.Part?,
		eventId: Int,
	): Response<PhotoResponse, String> {
		val response = eventCreateApi
			.sendEventAdditionalImage(
				authorization = "Bearer $authorization",
				image = image,
				event_id = eventId,
			)
			.execute()


		if (response.isSuccessful) return Response.Success(response.body()!!)
		val message = JSONObject(response.errorBody()?.string()!!).getString("message") ?: ""
		return Response.Error(message)
	}

	fun sendEventPrice(
		token: String,
		disList: List<String>,
		eventId: Int,
		price: Int,
		commission: Float,
		numberStep: Int,
	): Response<StepDataApiResponse, String> {
		val authorization = "Bearer $token"

		val fieldMap = mutableMapOf<String, Any>()
//		dateList.forEachIndexed { index, cat ->
//			fieldMap["discounts[$index][discount]"] = 10
//			fieldMap["discounts[$index][condition_count]"] = 10
//		}

		fieldMap["discounts[0][discount]"] = 10
		fieldMap["discounts[0][condition_count]"] = 10

		try {
			val response = eventCreateApi
				.sendEventPrice(
					commission =commission,
					price = price,
					eventId = eventId,
					fields = fieldMap,
					authorization = authorization,
					numberStep = numberStep
				)
				.execute()

			if (response.isSuccessful) return Response.Success(response.body()!!)
			val message = JSONObject(response.errorBody()?.string()!!).getString("message")
			return Response.Error(message)
		} catch (e: Exception) {
			return Response.Error(e.localizedMessage)
		}
	}
}