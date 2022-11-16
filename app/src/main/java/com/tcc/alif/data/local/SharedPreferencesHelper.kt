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

    var userEmail: String?
        get() = sharedPreferences.getString(USER_EMAIL, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_EMAIL,value).apply()

    var userPassword: String?
        get() = sharedPreferences.getString(USER_PASSWORD, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_PASSWORD, value).apply()

    var userName: String?
        get() = sharedPreferences.getString(USER_NAME, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_NAME, value).apply()

    var userDocument: String?
        get() = sharedPreferences.getString(USER_DOCUMENT, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_DOCUMENT, value).apply()

    var userCellphone: String?
        get() = sharedPreferences.getString(USER_CELLPHONE, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_CELLPHONE, value).apply()

    var userBirthday: String?
        get() = sharedPreferences.getString(USER_BIRTHDAY, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_BIRTHDAY, value).apply()

    var companyId: String?
        get() = sharedPreferences.getString(COMPANY_ID, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_ID, value).apply()

    var companyName: String?
        get() = sharedPreferences.getString(COMPANY_NAME, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_NAME, value).apply()

    companion object{
        const val SHARED_PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}.ENCRYPT_SHARED_PREFERENCES"
        private const val USER_ID = "$SHARED_PREFERENCES_NAME.USER_ID"
        private const val USER_EMAIL = "$SHARED_PREFERENCES_NAME.USER_EMAIL"
        private const val USER_PASSWORD = "$SHARED_PREFERENCES_NAME.USER_PASSWORD"
        private const val USER_NAME = "$SHARED_PREFERENCES_NAME.USER_NAME"
        private const val USER_DOCUMENT = "$SHARED_PREFERENCES_NAME.USER_DOCUMENT"
        private const val USER_CELLPHONE = "$SHARED_PREFERENCES_NAME.USER_CELLPHONE"
        private const val USER_BIRTHDAY = "$SHARED_PREFERENCES_NAME.USER_BIRTHDAY"
        private const val COMPANY_ID = "$SHARED_PREFERENCES_NAME.COMPANY_ID"
        private const val COMPANY_NAME = "$SHARED_PREFERENCES_NAME.COMPANY_NAME"
        const val EMPTY_STRING = ""
    }
}