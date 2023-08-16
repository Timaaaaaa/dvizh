package com.start.dvizk.create.steps.location

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

class LocationStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val requestCountryResponseStateLiveData: MutableLiveData<RequestResponseState> =
		CustomMutableLiveData()
	val requestCityResponseStateLiveData: MutableLiveData<RequestResponseState> =
		CustomMutableLiveData()
	val requestSendLocationResponseStateLiveData: MutableLiveData<RequestResponseState> =
		CustomMutableLiveData()

	fun getCountry() {
		requestCountryResponseStateLiveData.value = RequestResponseState.Loading

		launch(Dispatchers.IO) {
			val response = eventCreateRepository.getCountry()

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> requestCountryResponseStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> requestCountryResponseStateLiveData.value =
						RequestResponseState.Failed(response.error.toString())
				}
			}
		}
	}

	fun getCity() {
		requestCityResponseStateLiveData.value = RequestResponseState.Loading

		launch(Dispatchers.IO) {
			val response = eventCreateRepository.getCity()

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> requestCityResponseStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> requestCityResponseStateLiveData.value =
						RequestResponseState.Failed(response.error.toString())
				}
			}
		}
	}

	fun sendEventDescription(
		eventId: Int,
		country_id: Int,
		city_id: Int,
		apartment: Int?,
		street: String,
		description: String,
		authorization: String,
		numberStep: Int,
	) {
		requestSendLocationResponseStateLiveData.value = RequestResponseState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendEventLocation(
				eventId = eventId,
				country_id = country_id,
				city_id = city_id,
				apartment = apartment,
				street = street,
				description = description,
				authorization = authorization,
				numberStep = numberStep,
			)
			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> requestSendLocationResponseStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> requestSendLocationResponseStateLiveData.value =
						RequestResponseState.Failed(response.error.toString())
				}
			}
		}
	}
}