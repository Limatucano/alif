package com.tcc.alif.data.local

import android.content.SharedPreferences
import com.tcc.alif.BuildConfig
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    var userId: String?
        get() = sharedPreferences.getString(USER_ID, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_ID,value).apply()

    companion object{
        const val SHARED_PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}.ENCRYPT_SHARED_PREFERENCES"
        private const val USER_ID = "$SHARED_PREFERENCES_NAME.USER_ID"
        const val EMPTY_STRING = ""
    }
}