package com.start.dvizk.create.steps.price

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.calendar.model.EventDateTimeInterval
import com.start.dvizk.create.steps.data.EventCreateRepository
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PriceStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

	fun sendEventPrice(
		token: String,
		disList: List<String>,
		eventId: Int,
		price: Int,
		commission: Float,
		numberStep: Int,
	) {
		requestResponseStateLiveData.value = RequestResponseState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendEventPrice(
				commission =commission,
				price = price,
				eventId = eventId,
				disList = disList,
				token = token,
				numberStep = numberStep
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