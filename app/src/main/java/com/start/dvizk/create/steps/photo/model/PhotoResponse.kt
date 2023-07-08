package com.start.dvizk.create.steps.photo.model

data class PhotoResponse(
	val message: String,
	val data: PhotoData
)

data class PhotoData(
	val id: Int?,
	val image: String,
	val event_id: Int?
)