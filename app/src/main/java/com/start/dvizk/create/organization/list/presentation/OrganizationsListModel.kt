package com.start.dvizk.create.organization.list.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.organization.create.presentation.model.CurrentStepState
import com.start.dvizk.create.organization.list.data.OrganizationListRepository
import com.start.dvizk.create.organization.list.presentation.adapter.OnOrganizationItemClickListener
import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.create.organization.list.presentation.model.OrganizationListState
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OrganizationsListModel(
	private val organizationListRepository: OrganizationListRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope,
	OnOrganizationItemClickListener {

	val organizationListStateLiveData: MutableLiveData<OrganizationListState> = CustomMutableLiveData()
	val currentStepStateLiveData: MutableLiveData<CurrentStepState> = CustomMutableLiveData()

	var organizationId: Int = 0

	fun getPopularEvents(userId: Int) {
		launch(Dispatchers.IO) {
			val response = organizationListRepository.getPopularEvents(userId)

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

	fun getCurrentStep(token: String) {
		launch(Dispatchers.IO) {
			val response = organizationListRepository.getCurrentStep(token = token, organizationId = organizationId)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> currentStepStateLiveData.value =
						CurrentStepState.Success(response.result)
					is Response.Error -> currentStepStateLiveData.value =
						CurrentStepState.Failed(response.error.toString())
				}
			}
		}
	}

	override fun onItemClick(data: Organization) {
		organizationId = data.id
	}
}