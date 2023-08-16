package com.start.dvizk.create.steps.teamcount

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

class TeamCountStepViewModel(
	private val eventCreateRepository: EventCreateRepository,
	override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
	CoroutineScope {

	val requestTeamCountResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

	fun sendTeamCount(
		token: String,
		numberStep: Int,
		maximum_number_teams: Int,
		maximum_number_participants_team: Int,
		eventId: Int,
	) {
		requestTeamCountResponseStateLiveData.value = RequestResponseState.Loading
		launch(Dispatchers.IO) {
			val response = eventCreateRepository.sendTeamCount(
				token = token,
				numberStep = numberStep,
				maximum_number_participants_team = maximum_number_participants_team,
				maximum_number_teams = maximum_number_teams,
				eventId = eventId,
			)
			launch(Dispatchers.Main) {
				when (response) {
					is Response.Success -> requestTeamCountResponseStateLiveData.value =
						RequestResponseState.Success(response.result)
					is Response.Error -> requestTeamCountResponseStateLiveData.value =
						RequestResponseState.Failed(response.error.toString())
				}
			}
		}
	}
}