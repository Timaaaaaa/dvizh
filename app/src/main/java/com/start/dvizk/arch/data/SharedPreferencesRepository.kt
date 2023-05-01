package com.start.dvizk.arch.data

import android.content.Context

class SharedPreferencesRepository(context: Context) : SharedPreferencesContract {
	override val MY_PREFS_NAME = "MyPrefsFile"
	override val KEY_USER_NAME = "user_name"
	override val KEY_USER_AGE = "user_age"
	override val KEY_USER_TOKEN = "user_token"

	val no_value = ""

	private val prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)

	fun getUserName(): String {
		return prefs.getString(KEY_USER_NAME, no_value) ?: no_value
	}

	fun setUserName(name: String) {
		prefs.edit().putString(KEY_USER_NAME, name).apply()
	}

	fun getUserAge(): Int {
		return prefs.getInt(KEY_USER_AGE, 0)
	}

	fun setUserAge(age: Int) {
		prefs.edit().putInt(KEY_USER_AGE, age).apply()
	}

	fun getUserToken(): String {
		return prefs.getString(KEY_USER_TOKEN, no_value) ?: no_value
	}

	fun setUserToken(name: String) {
		prefs.edit().putString(KEY_USER_TOKEN, name).apply()
	}

	fun clearAll() {
		prefs.edit().clear().apply()
	}
}