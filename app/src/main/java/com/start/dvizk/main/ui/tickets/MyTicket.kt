package com.start.dvizk.main.ui.tickets

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

//@JsonIgnoreProperties(ignoreUnknown = true)
//data class MyTicket(
//	@JsonProperty("image")
//	val image: Int,
//	@JsonProperty("title")
//	val title: String,
//	@JsonProperty("date")
//	val date: String,
//	@JsonProperty("location")
//	val location: String
//)

data class MyTicket(
	val image: Int,
	val title: String,
	val date: String,
	val location: String
)
