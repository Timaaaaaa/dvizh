package com.start.dvizk.create.steps.calendar.model

import com.google.gson.annotations.SerializedName

data class EventDateTimeInterval (
	var date: String,
	var startTime: String?,
	var duration: String?,
	var durationViewText: String?,
)