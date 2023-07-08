package com.start.dvizk.create.steps.visitperson

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

class AllowedGuestStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val requestResponseStateLiveData: MutableLiveData<RequestResponseState> =
		CustomMutableLiveData()

	fun sendEventAllowedGuest(
		eventId: Int,
		age: Int,
		children: Int,
		additional_requirements: String,
		authorization: String,
		numberStep: Int,
	) {
		requestResponseStateLiveData.value = RequestResponseState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendEventAllowedGuest(
				eventId = eventId,
				age = age,
				children = children,
				additional_requirements = additional_requirements,
				numberStep = numberStep,
				authorization = authorization,
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