package com.start.dvizk.main.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class NotificationsViewModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
		value = "This is notifications Fragment"
	}

	suspend fun fff() {
		delay(2000)
		_text.value = "2222"
	}
	val text: LiveData<String> = _text
}