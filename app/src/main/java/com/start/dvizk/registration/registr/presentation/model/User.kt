package com.start.dvizk.registration.registr.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
	val email: String,
	val name: String,
	val nickname: String,
	val phone_number: String,
	val gender: String,
	val birthday: String,
	val imageFilePath: String,
	var password: String?
): Parcelable