package com.start.dvizk.main.ui.tickets.mytickets.data.model.state

import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket

sealed class CanceledTicketsState {

	object Loading: CanceledTicketsState()

	data class Failed(
		val message: String
	): CanceledTicketsState()

	data class Success(
		val canceledTickets: MutableList<MyTicket>,
		val total: Int
	): CanceledTicketsState()

}
