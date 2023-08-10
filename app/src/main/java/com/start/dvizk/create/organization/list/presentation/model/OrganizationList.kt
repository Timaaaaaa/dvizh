package com.start.dvizk.create.organization.list.presentation.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class OrganizationList(
	val message: String,
	val data: List<Organization>
)