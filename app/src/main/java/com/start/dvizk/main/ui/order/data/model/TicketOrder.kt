package com.start.dvizk.main.ui.order.data.model

data class TicketOrder(
	val order: TicketOrderScreen,
	val screen: String
)

data class TicketOrderScreen(
	val id: Int,
	val seconds_left: Int
)

data class TicketOwnerData(
	val name: String?,
	val surname: String?,
	val email: String?,
	val birthday: String?,
	val number: String?
)

data class TeamMember(
	val name: String
)

data class TeamData(
	val name: String,
	val members: List<TeamMember>
)