package com.start.dvizk.main.ui.profile.data.model

data class UserProfile(
	val user: ProfileDataModel
)

data class ProfileDataModel(
	val id: Int,
	val name: String,
	val eventsCount: Int,
	val subscriptions: Int,
	val subscribers: Int
)
