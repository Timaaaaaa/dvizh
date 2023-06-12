package com.start.dvizk.create.organization.list.presentation.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
class CurrentStep(
	@JsonProperty("name")
	val name: String,
	@JsonProperty("event_data")
	val event_data: Any,
	@JsonProperty("event_id")
	val event_id: Int,
	@JsonProperty("number_step")
	val number_step: Int
)