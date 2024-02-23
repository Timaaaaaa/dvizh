package com.start.dvizk.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
	
	fun getClient(): Retrofit {

		val client = OkHttpClient().newBuilder().build()
		return Retrofit.Builder()
			.baseUrl("")
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()
	}
}
