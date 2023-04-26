package com.start.dvizk.main.ui.home.presentation.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.JsonObject

@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
	@JsonProperty("id")
	val id: Long,
	@JsonProperty("main_image")
	val main_image: String,
	@JsonProperty("name")
	val name: String,
)