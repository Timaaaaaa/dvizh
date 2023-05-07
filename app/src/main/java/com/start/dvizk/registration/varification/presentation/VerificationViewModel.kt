package com.start.dvizk.registration.varification.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.dvizk.arch.CustomMutableLiveData
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.network.Response
import com.start.dvizk.registration.registr.domain.VerificationRepository
import com.start.dvizk.registration.registr.presentation.RegistrationState
import com.start.dvizk.registration.registr.presentation.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import kotlin.coroutines.CoroutineContext

class VerificationViewModel(
	private val verificationRepo: VerificationRepository,
	private val sharedPreferencesRepository: SharedPreferencesRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val verificationStateLiveData: MutableLiveData<VerificationState> = CustomMutableLiveData()

	fun verify(
		email: String,
		verificationCode: String
	) {
		verificationStateLiveData.value = VerificationState.Loading
		launch(Dispatchers.IO) {
			val response = verificationRepo.verify(
				email = email,
				verificationCode = verificationCode
			)
			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> {
						setUserData(response.result)
						verificationStateLiveData.value =
							VerificationState.Success(null)
					}
					is Response.Error -> verificationStateLiveData.value =
						VerificationState.Failed(response.error.toString())
				}
			}
		}
	}

	private fun setUserData(user: User) {
		sharedPreferencesRepository.setUserToken(user.token)
		user.id?.let {
			sharedPreferencesRepository.setUserId(it)
		}
		sharedPreferencesRepository.setUserName(user.name)
	}
}