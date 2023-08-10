package com.start.dvizk.main.ui.tickets.mytickets.data.model.state

import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket

sealed class UpcomingTicketsState {

	object Loading: UpcomingTicketsState()

	data class Failed(
		val message: String
	): UpcomingTicketsState()

	data class Success(
		val upcomingTickets: MutableList<MyTicket>,
		val totalPage: Int
	): UpcomingTicketsState()

}
