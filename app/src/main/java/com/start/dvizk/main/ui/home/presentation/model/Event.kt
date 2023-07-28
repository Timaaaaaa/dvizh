package com.start.dvizk.main.ui.home.presentation.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
	@JsonProperty("id")
	val id: Long,
	@JsonProperty("main_image")
	val main_image: String,
	@JsonProperty("name")
	val name: String,
	@JsonProperty("is_favorite")
	var is_favorite: Boolean,
): Parcelable