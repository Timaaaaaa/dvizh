package com.start.dvizk.main.ui.detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailPageModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
		value = "This is event page Fragment"
	}
	val text: LiveData<String> = _text
}