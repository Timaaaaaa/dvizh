package com.start.dvizk.main.ui.profile.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.profile.data.ProfileRepository
import com.start.dvizk.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(
	private val profileRepository: ProfileRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val profileStateLiveData: MutableLiveData<RequestResponseState>	= CustomMutableLiveData()

	fun getUserProfile(token: String) {
		launch(Dispatchers.IO) {
			val response = profileRepository.getUserProfile(token)

			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> profileStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> profileStateLiveData.value =
						RequestResponseState.Failed(response.error)
				}
			}
		}
	}
}