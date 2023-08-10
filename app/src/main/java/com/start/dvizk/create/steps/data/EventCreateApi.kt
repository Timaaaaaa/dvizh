package com.start.dvizk.create.steps.data

import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.language.model.EventParameter
import com.start.dvizk.create.steps.photo.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface EventCreateApi {

	@FormUrlEncoded
	@POST("/api/v3/event/{number_step}")
	fun sendEventType(
		@Header("Authorization") token: String,
		@Path("number_step") numberStep: Int,
		@Field("type") type: String,
		@Field("event_id") eventId: Int,
	): Call<StepDataApiResponse>

	@GET("/api/v2/language")
	fun getLanguages(): Call<List<EventParameter>>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	 fun sendEventLanguages(
		@Field("event_id") eventId: Int,
		@FieldMap fields: Map<String, @JvmSuppressWildcards Any>,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventCategory(
		@Field("event_id") eventId: Int,
		@FieldMap fields: Map<String, @JvmSuppressWildcards Any>,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventDescription(
		@Field("event_id") eventId: Int,
		@Field("description") description: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@GET("/api/v2/country")
	fun getCountry(): Call<List<EventParameter>>

	@GET("/api/v2/city")
	fun getCity(): Call<List<EventParameter>>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventLocation(
		@Field("event_id") eventId: Int,
		@Field("country_id") country_id: Int,
		@Field("city_id") city_id: Int,
		@Field("apartment") apartment: Int,
		@Field("street") street: String,
		@Field("description") description: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventDate(
		@Field("event_id") eventId: Int,
		@FieldMap fields: Map<String, @JvmSuppressWildcards Any>,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventPurchaseDeadline(
		@Field("event_id") eventId: Int,
		@Field("purchase_deadline") purchase_deadline: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventService(
		@Field("event_id") eventId: Int,
		@Field("additional_services") additional_services: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventNecessaryItems(
		@Field("event_id") eventId: Int,
		@FieldMap fields: Map<String, @JvmSuppressWildcards Any>,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventAllowedGuest(
		@Field("event_id") eventId: Int,
		@Field("age") age: Int,
		@Field("children") children: Int,
		@Field("additional_requirements") additional_requirements: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventName(
		@Field("event_id") eventId: Int,
		@Field("name") name: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventImage(
		@Field("event_id") eventId: Int,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@Multipart
	@POST("/api/v3/event/{event_id}/image")
	fun sendEventMainImage(
		@Header("Authorization") authorization: String,
		@Path("event_id") event_id: Int,
		@Part image: MultipartBody.Part?,
	): Call<PhotoResponse>

	@Multipart
	@POST("/api/v3/event/{event_id}/additional_image")
	fun sendEventAdditionalImage(
		@Header("Authorization") authorization: String,
		@Path("event_id") event_id: Int,
		@Part image: MultipartBody.Part?,
	): Call<PhotoResponse>

	@FormUrlEncoded
	@POST("/api/v3/event/{number_step}")
	fun sendEventClassification(
		@Header("Authorization") token: String,
		@Path("number_step") numberStep: Int,
		@Field("classification") classification: String,
		@Field("event_id") eventId: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("/api/v3/event/{number_step}")
	fun sendEventMaxGroupSize(
		@Header("Authorization") token: String,
		@Path("number_step") numberStep: Int,
		@Field("maximum_group_size") maximum_group_size: Int,
		@Field("event_id") eventId: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("/api/v3/event/{number_step}")
	fun sendEventEntryCondition(
		@Header("Authorization") token: String,
		@Path("number_step") numberStep: Int,
		@Field("entry_condition") entry_condition: String,
		@Field("event_id") eventId: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventPrice(
		@Field("event_id") eventId: Int,
		@Field("price") price: Int,
		@Field("commission") commission: Float,
		@FieldMap fields: Map<String, @JvmSuppressWildcards Any>,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventCancelRule(
		@Field("event_id") eventId: Int,
		@Field("cancellation_rules") cancellation_rules: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>

	@FormUrlEncoded
	@POST("api/v3/event/{number_step}")
	fun sendEventRule(
		@Field("event_id") eventId: Int,
		@Field("rules") rules: String,
		@Header("Authorization") authorization: String,
		@Path("number_step") numberStep: Int,
	): Call<StepDataApiResponse>
}