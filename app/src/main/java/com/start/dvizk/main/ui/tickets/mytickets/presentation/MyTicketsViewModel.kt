package com.start.dvizk.main.ui.tickets.mytickets.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.main.ui.tickets.mytickets.data.MyTicketsRepository
import com.start.dvizk.main.ui.tickets.mytickets.data.model.state.CanceledTicketsState
import com.start.dvizk.main.ui.tickets.mytickets.data.model.state.FinishedTicketsState
import com.start.dvizk.main.ui.tickets.mytickets.data.model.state.UpcomingTicketsState
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyTicketsViewModel(
	private val myTicketsRepository: MyTicketsRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val userUpcomingTicketsStateLiveData: MutableLiveData<UpcomingTicketsState>	= CustomMutableLiveData()
	val userFinishedTicketsStateLiveData: MutableLiveData<FinishedTicketsState>	= CustomMutableLiveData()
	val userCanceledTicketsStateLiveData: MutableLiveData<CanceledTicketsState>	= CustomMutableLiveData()

	fun getUserUpcomingTickets(
		page: Int,
		token: String
	) {
		userUpcomingTicketsStateLiveData.value = UpcomingTicketsState.Loading

		launch(Dispatchers.IO) {
			val response = myTicketsRepository.getUserUpcomingTickets(page, token)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> userUpcomingTicketsStateLiveData.value =
						UpcomingTicketsState.Success(response.result.tickets, response.result.totalPage)
					is Response.Error -> userUpcomingTicketsStateLiveData.value =
						UpcomingTicketsState.Failed(response.error)
				}
			}
		}
	}

	fun getUserFinishedTickets(
		page: Int,
		token: String
	) {
		userFinishedTicketsStateLiveData.value = FinishedTicketsState.Loading

		launch(Dispatchers.IO) {
			val response = myTicketsRepository.getUserFinishedTickets(page, token)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> userFinishedTicketsStateLiveData.value =
						FinishedTicketsState.Success(response.result, 0)
					is Response.Error -> userFinishedTicketsStateLiveData.value =
						FinishedTicketsState.Failed(response.error)
				}
			}
		}
	}

	fun getUserCanceledTickets(
		page: Int,
		token: String
	) {
		userCanceledTicketsStateLiveData.value = CanceledTicketsState.Loading

		launch(Dispatchers.IO) {
			val response = myTicketsRepository.getUserCancelledTickets(page, token)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> userCanceledTicketsStateLiveData.value =
						CanceledTicketsState.Success(response.result, 0)
					is Response.Error -> userCanceledTicketsStateLiveData.value =
						CanceledTicketsState.Failed(response.error)
				}
			}
		}
	}
}