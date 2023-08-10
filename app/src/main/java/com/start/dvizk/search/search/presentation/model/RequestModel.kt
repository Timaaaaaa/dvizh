package com.start.dvizk.search.search.presentation.model

data class RequestModel(
	val page: Int? = null,
	val categories: List<Int>? = null,
	val price: Price? = null,
	val languages: List<Int>? = null,
	val dateRange: DateRange? = null,
	val classification: String? = null,
	val ticketQuantities: TicketQuantities? = null,
	val months: List<Int>? = null,
	val cityId: Int? = null,
	val flexibleDateType: String? = null
)

data class Price(
	val min: Int,
	val max: Int
)

data class TicketQuantities(
	val adult: Int,
	val child: Int
)

	data class DateRange(
	val from: String? = null,
	val to: String? = null,
	val extendedRange: Int = 0
)