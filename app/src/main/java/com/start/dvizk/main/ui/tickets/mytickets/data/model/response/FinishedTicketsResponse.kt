package com.start.dvizk.main.ui.tickets.mytickets.data.model.response

import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket

data class FinishedTicketsResponse(
	val tickets: MutableList<MyTicket>,
	val page: Int,
	val nbTotal: Int,
	val totalPage: Int
)
