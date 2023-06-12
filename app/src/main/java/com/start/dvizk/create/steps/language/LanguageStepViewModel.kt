package com.start.dvizk.create.steps.language

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.main.ui.home.data.HomePageRepository
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LanguageStepViewModel(
	private val languagesListApi: LanguagesListApi,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val categoriesListState: MutableLiveData<CategoriesListState> = CustomMutableLiveData()

	fun getLanguages(parent_id: Int?) {
		categoriesListState.value = CategoriesListState.Loading

		launch(Dispatchers.IO) {
			val response = languagesListApi.getLanguages()

			launch(Dispatchers.Main) {

			}
		}
	}
}