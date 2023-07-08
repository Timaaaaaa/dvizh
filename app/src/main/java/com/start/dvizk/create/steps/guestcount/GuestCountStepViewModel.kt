package com.start.dvizk.create.steps.guestcount

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

class GuestCountStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

	fun sendEventMaxGroupSize(
		token: String,
		numberStep: Int,
		maximum_group_size: Int,
		eventId: Int,
	) {
		requestResponseStateLiveData.value = RequestResponseState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendEventMaxGroupSize(
				token = token,
				numberStep = numberStep,
				maximum_group_size = maximum_group_size,
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