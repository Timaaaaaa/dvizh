package com.start.dvizk.create.steps.language.model

import com.google.gson.annotations.SerializedName

data class EventParameter (
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String,
	var isSelected: Boolean = false
)