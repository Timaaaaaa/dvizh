package com.start.dvizk.search.search.presentation.model

data class MonthModel(
	val id: Int,
	val monthName: String,
	val year: Int,
	val monthNumber: Int,
	var isSelected: Boolean = false
)