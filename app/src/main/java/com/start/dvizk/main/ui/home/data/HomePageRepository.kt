package com.start.dvizk.main.ui.home.data

import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.network.ApiErrorExceptionFactory
import com.start.dvizk.network.Response
import org.json.JSONObject

class HomePageRepository(
	private val homePageApi: HomePageApi,
	private val apiErrorExceptionFactory: ApiErrorExceptionFactory
) {

	fun getPopularEvents(
		page: Int
	): Response<List<Event>, String> {
		val response = homePageApi
			.getPopularEvents(
				page = page
			)
			.execute()

		if(response.isSuccessful) return Response.Success(response.body()!!.events)

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}

	fun getCategories(): Response<List<Category>, String> {
		val response = homePageApi
			.getCategories()
			.execute()

		if(response.isSuccessful) return Response.Success(response.body()!!)

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}