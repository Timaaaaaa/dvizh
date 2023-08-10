package com.start.dvizk.create.steps.calendar

import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.create.steps.calendar.model.EventDateTimeInterval
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event

interface OnSelectTimeListener {

	fun onStartTimeSelect(item: EventDateTimeInterval)

	fun onDurationTimeSelect(item: EventDateTimeInterval)
}