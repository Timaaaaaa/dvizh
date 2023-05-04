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

	fun getUpcomingEvents(
		page: Int,
		categoryId: Int,
	): Response<List<Event>, String> {
		val response = homePageApi
			.getUpcomingEvents(
				page = page,
				categoryId = categoryId
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

		if(response.isSuccessful) {
			response.body()?.add(0, Category(
				id = 0,
				parent_id = 0,
				name = "Все",
				isSelected = true,
				image = "https://www.google.com/search?q=%D0%B2%D1%81%D0%B5+%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0+%D0%B1%D0%B5%D0%B7+%D0%B7%D0%B0%D0%B4%D0%BD%D0%B5%D0%B3%D0%BE+%D1%84%D0%BE%D0%BD%D0%B0&tbm=isch&ved=2ahUKEwjL0O6doMr-AhUGtyoKHcESBHoQ2-cCegQIABAA&oq=%D0%B2%D1%81%D0%B5+%D0%B8%D0%BA%D0%BE%D0%BD%D0%BA%D0%B0+%D0%B1%D0%B5%D0%B7+%D0%B7%D0%B0%D0%B4%D0%BD%D0%B5%D0%B3%D0%BE+%D1%84%D0%BE%D0%BD%D0%B0&gs_lcp=CgNpbWcQAzoECCMQJzoGCAAQCBAeOgQIABAeUPkCWKIoYNMqaAFwAHgAgAHpAYgBlxiSAQYwLjE4LjGYAQCgAQGqAQtnd3Mtd2l6LWltZ8ABAQ&sclient=img&ei=8INKZMuYG4buqgHBpZDQBw&bih=674&biw=1392#imgrc=qWFX9L_hOdZ5oM",
			))
			return Response.Success(response.body()!!)
		}

		val message = JSONObject(response.errorBody()?.string()!!).getString("message")
		return Response.Error(message)
	}
}