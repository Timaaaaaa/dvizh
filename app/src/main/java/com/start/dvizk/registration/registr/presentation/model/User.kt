package com.start.dvizk.registration.registr.presentation.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
	@JsonProperty("id")
	val id: Long? = -1,
	@JsonProperty("email")
	val email: String,
	@JsonProperty("name")
	val name: String,
	@JsonProperty("nickname")
	val nickname: String,
	@JsonProperty("token")
	val token: String,
	@JsonProperty("phone_number")
	val phone_number: String,
	@JsonProperty("gender")
	val gender: String,
	@JsonProperty("birthday")
	val birthday: String,
	@JsonProperty("password")
	var password: String? = null,
	@JsonProperty("status")
	val status: String? = null,
	@JsonProperty("image")
	val image: String? = null
): Parcelable