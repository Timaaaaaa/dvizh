package com.start.dvizk.main.ui.detail.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.organization.list.presentation.model.OrganizationListState
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

	val organizationListStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

	fun getCancellationRules(eventId: Int) {
		launch(Dispatchers.IO) {
			val response = eventDetailRepository.getCancellationRules(eventId)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> organizationListStateLiveData.value =
						OrganizationListState.Success(response.result)
					is Response.Error -> organizationListStateLiveData.value =
						OrganizationListState.Failed(response.error.toString())
				}
			}
		}
	}
}