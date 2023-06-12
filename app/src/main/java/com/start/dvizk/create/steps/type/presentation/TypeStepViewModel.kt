package com.start.dvizk.create.steps.type.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.data.EventCreateApi
import com.start.dvizk.create.steps.data.EventCreateRepository
import com.start.dvizk.network.Response
import com.start.dvizk.registration.registr.data.RegistrationRepository
import com.start.dvizk.registration.registr.presentation.RegistrationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import kotlin.coroutines.CoroutineContext

class TypeStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val registrationStateLiveData: MutableLiveData<RegistrationState> = CustomMutableLiveData()

	fun sendEventType(
		token: String,
		numberStep: Int,
		type: String,
		eventId: Int,
	) {
		registrationStateLiveData.value = RegistrationState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendEventType(
				token = token,
				numberStep = numberStep,
				type = type,
				eventId = eventId,
			)
			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> registrationStateLiveData.value =
						RegistrationState.Success(response.result)
					is Response.Error -> registrationStateLiveData.value =
						RegistrationState.Failed(response.error.toString())
				}
			}
		}
	}
}