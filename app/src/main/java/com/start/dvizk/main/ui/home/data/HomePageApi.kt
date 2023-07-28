package com.start.dvizk.main.ui.home.data

import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.EventResponse
import com.start.dvizk.search.search.presentation.model.RequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface HomePageApi {

	@GET("/api/v2/event/popular")
	fun getPopularEvents(
		@Query("page") page: Int
	): Call<EventResponse>

	@GET("/api/v2/event_category")
	fun getCategories(
		@Query("parent_id") parent_id: Int?
	): Call<MutableList<Category>>

	@GET("/api/v2/event/upcoming")
	fun getUpcomingEvents(
		@Query("category_id") categoryId: Int,
		@Query("page") page: Int,
	): Call<EventResponse>

	@Headers("Content-Type: application/json")
	@POST("/api/v2/event")
	fun getSearchedEvents(
		@Header("Authorization") token: String,
		@Body request: RequestModel
	): Call<EventResponse>
}