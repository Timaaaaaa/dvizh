package com.start.dvizk.main.ui.home.presentation.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Event(
	val id: Long,
	val name: String,
	val datetime: @RawValue EventDateTime,
	val location: @RawValue EventLocation,
	val main_image: String,
	var is_favorite: Boolean
): Parcelable

data class EventDateTime(
	val id: Long,
	val start: String,
	val duration: String,
	val event_id: Long,
	val created_at: String,
	val updated_at: String
)

data class EventLocation(
	val id: Long,
	val country: EventCountry,
	val city: EventCity,
	val apartment: String,
	val finding_information: String
)

data class EventCountry(
	val id: Int,
	val name: String,
	val created_at: String,
	val updated_at: String
)

data class EventCity(
	val id: Int,
	val name: String,
	val created_at: String,
	val updated_at: String
)