package com.start.dvizk.main.ui.tickets.ticket.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.tickets.ticket.data.TicketRepository
import com.start.dvizk.main.ui.tickets.ticket.data.model.TicketCancelingState
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TicketViewModel(
	private val ticketRepository: TicketRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val userTicketStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
	val userTicketCancelingState: MutableLiveData<TicketCancelingState> = CustomMutableLiveData()

	fun getTicketById(
		ticketId: Int,
		token: String
	) {
		userTicketStateLiveData.value = RequestResponseState.Loading

		launch(Dispatchers.IO) {
			val response = ticketRepository.getTicketById(ticketId, token)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> userTicketStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> userTicketStateLiveData.value =
						RequestResponseState.Failed(response.error)
				}
			}
		}
	}

	fun cancelTicket(
		ticketId: Int,
		token: String,
		ticket_cancel_reason_id: Int,
		rating: Int,
		review: String
	) {
		userTicketCancelingState.value = TicketCancelingState.Loading

		launch(Dispatchers.IO) {
			val response = ticketRepository.cancelTicket(
				ticketId,
				token,
				ticket_cancel_reason_id,
				rating,
				review
			)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> userTicketCancelingState.value =
						TicketCancelingState.Success(response.result)
					is Response.Error -> userTicketCancelingState.value =
						TicketCancelingState.Failed(response.error)
				}
			}
		}
	}
}