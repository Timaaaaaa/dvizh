package com.start.dvizk.arch.data

import android.content.Context

class SharedPreferencesRepository(context: Context) : SharedPreferencesContract {
	override val MY_PREFS_NAME = "MyPrefsFile"
	override val KEY_USER_NAME = "user_name"
	override val KEY_USER_ID = "user_id"
	override val KEY_USER_TOKEN = "user_token"
	override val FIRST_LAUNCH_INSTRUCTIOn = "first_launch_instruction"

	val no_value = ""

	private val prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)

	fun getUserName(): String {
		return prefs.getString(KEY_USER_NAME, no_value) ?: no_value
	}

	fun setUserName(name: String) {
		prefs.edit().putString(KEY_USER_NAME, name).apply()
	}

	fun getUserId(): Long {
		return prefs.getLong(KEY_USER_ID, 0)
	}

	fun setUserId(id: Long) {
		prefs.edit().putLong(KEY_USER_ID, id).apply()
	}

	fun getUserToken(): String {
		return prefs.getString(KEY_USER_TOKEN, no_value) ?: no_value
	}

	fun setUserToken(name: String) {
		prefs.edit().putString(KEY_USER_TOKEN, name).apply()
	}

	fun setFirstLaunchInstructio(value: Boolean) {
		prefs.edit().putBoolean(FIRST_LAUNCH_INSTRUCTIOn, value).apply()
	}

	fun getFirstLaunchInstructio(): Boolean {
		return prefs.getBoolean(FIRST_LAUNCH_INSTRUCTIOn, true)
	}

	fun clearAll() {
		prefs.edit().clear().apply()
	}
}