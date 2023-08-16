package com.start.dvizk.create.steps.calendar.model


data class EventDateTimeInterval (
	var date: String,
	var startTime: String?,
	var duration: String?,
	var durationViewText: String?,
	var type: String? = "",
	var price: Int = -1,
	var ticketCount: Int = -1,
	var teamCount: Int = -1,
	var teamMemberCount: Int = -1,
)