package com.start.dvizk.main.ui.tickets.ticket.data.model

sealed class TicketCancelingState {

	object Loading: TicketCancelingState()

	data class Failed(
		val message: String
	): TicketCancelingState()

	data class Success(
		val message: String
	): TicketCancelingState()

}
