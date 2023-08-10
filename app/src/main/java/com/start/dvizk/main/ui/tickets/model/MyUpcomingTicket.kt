package com.start.dvizk.main.ui.tickets.model

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

data class MyUpcomingTicket(
	val image: Int,
	val title: String,
	val date: String,
	val location: String
)
