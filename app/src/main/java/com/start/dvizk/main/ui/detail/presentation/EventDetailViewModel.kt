package com.start.dvizk.main.ui.detail.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.detail.data.EventDetailRepository
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EventDetailViewModel(
	private val eventDetailRepository: EventDetailRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val eventDetailsStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
	val cancellationRulesStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
	val eventRulesStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
	val orderTicketStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

	fun getEventDetails(eventId: Int) {
		launch(Dispatchers.IO) {
			val response = eventDetailRepository.getEventDetails(eventId)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> eventDetailsStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> eventDetailsStateLiveData.value =
						RequestResponseState.Failed(response.error)
				}
			}
		}
	}

	fun getCancellationRules(eventId: Int) {
		launch(Dispatchers.IO) {
			val response = eventDetailRepository.getCancellationRules(eventId)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> cancellationRulesStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> cancellationRulesStateLiveData.value =
						RequestResponseState.Failed(response.error)
				}
			}
		}
	}

	fun getEventRules(eventId: Int) {
		launch(Dispatchers.IO) {
			val response = eventDetailRepository.getEventRules(eventId)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> eventRulesStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> eventRulesStateLiveData.value =
						RequestResponseState.Failed(response.error)
				}
			}
		}
	}

	fun orderFirstStep(datetimeId: Int) {
		launch(Dispatchers.IO) {
			orderTicketStateLiveData.value = RequestResponseState.Loading
			val response = eventDetailRepository.orderFirstStep(datetimeId)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> orderTicketStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> orderTicketStateLiveData.value =
						RequestResponseState.Failed(response.error)
				}
			}
		}
	}
}