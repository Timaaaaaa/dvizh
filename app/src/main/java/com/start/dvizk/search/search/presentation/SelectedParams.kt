package com.start.dvizk.search.search.presentation

import com.start.dvizk.search.search.presentation.model.DateRange
import com.start.dvizk.search.search.presentation.model.MonthModel

interface SelectedParams {

	fun onDateRangeSelected(dateRange: DateRange)

	fun onMonthListSelected(month: MonthModel)

}