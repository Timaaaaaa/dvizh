package com.start.dvizk.create.steps.language

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.data.EventCreateRepository
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.home.data.HomePageRepository
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LanguageStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
	val requestSendLangResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

	fun getLanguages() {
		requestResponseStateLiveData.value = RequestResponseState.Loading

		launch(Dispatchers.IO) {
			val response = eventCreateRepository.getLanguages()

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

	fun sendEventLanguages(
		token: String,
		languages: List<Int>,
		eventId: Int,
		numberStep: Int,
	) {
		requestSendLangResponseStateLiveData.value = RequestResponseState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendEventLanguages(
				token = token,
				languages = languages,
				eventId = eventId,
				numberStep = numberStep,
			)
			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> requestSendLangResponseStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> requestSendLangResponseStateLiveData.value =
						RequestResponseState.Failed(response.error.toString())
				}
			}
		}
	}
}