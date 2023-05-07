package com.start.dvizk.create.organization.list.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.organization.list.data.OrganizationListRepository
import com.start.dvizk.create.organization.list.presentation.model.OrganizationListState
import com.start.dvizk.main.ui.home.data.HomePageRepository
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.PopularEvetsState
import com.start.dvizk.main.ui.home.presentation.model.UpcomingEvetsState
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OrganizationsListModel(
	private val organizationListRepository: OrganizationListRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val organizationListStateLiveData: MutableLiveData<OrganizationListState> = CustomMutableLiveData()

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
}