package com.start.dvizk.create.organization.list.presentation.model

data class Organization(
	val id: Int,
	val image: String,
	val name: String,
	val description: String,
	val phoneNumber: String,
	val email: String,
	val whatsapp: String,
	val instagram: String,
	var isSelected: Boolean = false
)