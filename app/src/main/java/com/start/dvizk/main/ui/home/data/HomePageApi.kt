package com.start.dvizk.main.ui.home.data

import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.main.ui.home.presentation.model.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomePageApi {

	@GET("/api/v2/event/popular")
	fun getPopularEvents(
		@Query("page") page: Int
	): Call<EventResponse>

	@GET("/api/v2/event_category")
	fun getCategories(): Call<MutableList<Category>>

	@GET("/api/v2/event/upcoming")
	fun getUpcomingEvents(
		@Query("category_id") categoryId: Int,
		@Query("page") page: Int,
	): Call<EventResponse>
}