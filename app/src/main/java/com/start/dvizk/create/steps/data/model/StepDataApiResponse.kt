package com.start.dvizk.create.steps.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class StepDataApiResponse(
	@SerializedName("data")
	val data: Data
)

data class Data(
	@SerializedName("event_id")
	val eventId: Int,
	@SerializedName("previous_step")
	val previousStep: PreviousStep,
	@SerializedName("next_step")
	val nextStep: NextStep
)

data class PreviousStep(
	@SerializedName("number_step")
	val numberStep: Int
)

data class NextStep(
	@SerializedName("name")
	val name: String,
	@SerializedName("number_step")
	val numberStep: Int
)