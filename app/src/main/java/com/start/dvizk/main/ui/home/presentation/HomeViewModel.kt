package com.start.dvizk.main.ui.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.main.ui.home.data.HomePageRepository
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.PopularEvetsState
import com.start.dvizk.main.ui.home.presentation.model.UpcomingEvetsState
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
	private val homePageRepository: HomePageRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val popularEventsStateLiveData: MutableLiveData<PopularEvetsState> = CustomMutableLiveData()
	val upcomingEventsStateLiveData: MutableLiveData<UpcomingEvetsState> = CustomMutableLiveData()
	val categoriesListState: MutableLiveData<CategoriesListState> = CustomMutableLiveData()


	fun getPopularEvents() {
		launch(Dispatchers.IO) {
			val response = homePageRepository.getPopularEvents(1)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> popularEventsStateLiveData.value =
						PopularEvetsState.Success(response.result)
					is Response.Error -> popularEventsStateLiveData.value =
						PopularEvetsState.Failed(response.error.toString())
				}
			}
		}
	}

	fun getCategories() {
		launch(Dispatchers.IO) {
			val response = homePageRepository.getCategories(null)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> categoriesListState.value =
						CategoriesListState.Success(response.result)
					is Response.Error -> categoriesListState.value =
						CategoriesListState.Failed(response.error.toString())
				}
			}
		}
	}

	fun getUpcomingEvents(
		page: Int,
		categoryId: Int,
	) {
		upcomingEventsStateLiveData.value = UpcomingEvetsState.Loading
		launch(Dispatchers.IO) {
			val response = homePageRepository.getUpcomingEvents(page = page,categoryId = categoryId)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> upcomingEventsStateLiveData.value =
						UpcomingEvetsState.Success(response.result)
					is Response.Error -> upcomingEventsStateLiveData.value =
						UpcomingEvetsState.Failed(response.error.toString())
				}
			}
		}
	}
}