package com.start.dvizk.main.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventPageModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
		value = "This is event page Fragment"
	}
	val text: LiveData<String> = _text
}