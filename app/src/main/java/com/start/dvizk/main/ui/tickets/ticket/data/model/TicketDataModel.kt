package com.start.dvizk.main.ui.tickets.ticket.data.model

data class UserTicket(
	val ticket: TicketDataModel
)

data class TicketDataModel(
	val id: Int,
	val date: String,
	val time: String,
	val price: String
)
