package com.start.dvizk.main.ui.tickets.ticket.presentation

import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket

interface OnTicketClickListener {
	fun onViewTicket(data: MyTicket)
	fun onCancelTicket(data: MyTicket)
}