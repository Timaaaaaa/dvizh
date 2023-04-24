package com.start.dvizk.registration.registr.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class User(
	val email: String,
	val password: String,
	val name: String,
	val nickname: String,
	val phone_number: String,
	val gender: String,
	val birthday: String,
	val imageFilePath: String
)