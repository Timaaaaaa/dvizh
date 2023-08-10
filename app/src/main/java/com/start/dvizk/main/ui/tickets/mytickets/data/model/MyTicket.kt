package com.start.dvizk.main.ui.tickets.mytickets.data.model

data class MyTicket(
	val id: Int,
	val image: String,
	val name: String,
	val address: String,
	val datetime: TicketDateTime
)

data class TicketDateTime(
	val date: String,
	val startTime: String,
	val endTime: String
)
