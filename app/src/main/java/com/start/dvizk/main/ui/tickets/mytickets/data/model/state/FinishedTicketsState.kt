package com.start.dvizk.main.ui.tickets.mytickets.data.model.state

import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket

sealed class FinishedTicketsState {

	object Loading: FinishedTicketsState()

	data class Failed(
		val message: String
	): FinishedTicketsState()

	data class Success(
		val finishedTickets: MutableList<MyTicket>,
		val total: Int
	): FinishedTicketsState()

}
