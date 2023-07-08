package com.start.dvizk.create.steps.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.data.EventCreateRepository
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BookingStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

	fun sendEventPurchaseDeadline(
		token: String,
		numberStep: Int,
		purchaseDeadline: String,
		eventId: Int,
	) {
		requestResponseStateLiveData.value = RequestResponseState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendEventPurchaseDeadline(
				token = token,
				numberStep = numberStep,
				purchaseDeadline = purchaseDeadline,
				eventId = eventId,
			)
			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> requestResponseStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> requestResponseStateLiveData.value =
						RequestResponseState.Failed(response.error.toString())
				}
			}
		}
	}
}