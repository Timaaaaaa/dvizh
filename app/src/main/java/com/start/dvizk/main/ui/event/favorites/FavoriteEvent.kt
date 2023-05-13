package com.start.dvizk.main.ui.event.favorites

data class FavoriteEvent(
	val image: Int,
	val title: String,
	val date: String,
	val location: String,
	var isFavorite: Boolean
)
