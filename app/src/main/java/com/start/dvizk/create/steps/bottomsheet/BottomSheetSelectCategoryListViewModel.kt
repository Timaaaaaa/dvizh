package com.start.dvizk.create.steps.bottomsheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem
import com.start.dvizk.main.ui.home.data.HomePageRepository
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BottomSheetSelectCategoryListViewModel(
	private val homePageRepository: HomePageRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope,
	OnCategoryCheckItemClickListener {

	val categoriesListState: MutableLiveData<CategoriesListState> = CustomMutableLiveData()
	val navigation: MutableLiveData<Navigation> = CustomMutableLiveData()

	private var isSubCategoryList = false
	val categoryList = mutableListOf<Category>()

	fun getCategories(parent_id: Int?) {
		categoriesListState.value = CategoriesListState.Loading

		launch(Dispatchers.IO) {
			val response = homePageRepository.getCategories(parent_id)

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

	override fun onItemClick(data: Category) {

		if(data.is_sub) {
			getCategories(data.id.toInt())
			isSubCategoryList = true

			return
		}

		if (categoryList.contains(data)) {
			categoryList.remove(data)
		} else {
			categoryList.add(data)
		}
	}

	fun navigateBack() {
		if (isSubCategoryList) {
			isSubCategoryList = false
			navigation.value = Navigation.OnSubCategoryBack
		} else {
			navigation.value = Navigation.OnCategoryBack
		}
	}

}