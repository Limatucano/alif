package com.tcc.alif.data.local

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
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

    var companyCategory: String?
        get() = sharedPreferences.getString(COMPANY_CATEGORY, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_CATEGORY, value).apply()

    var companyDocument : String?
        get() = sharedPreferences.getString(COMPANY_DOCUMENT, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_DOCUMENT, value).apply()

    var companyOwner : String?
        get() = sharedPreferences.getString(COMPANY_OWNER, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_OWNER, value).apply()

    var companyState : String?
        get() = sharedPreferences.getString(COMPANY_STATE, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_STATE, value).apply()

    var companyZipCode : String?
        get() = sharedPreferences.getString(COMPANY_ZIP_CODE, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_ZIP_CODE, value).apply()

    var companyTelephone : String?
        get() = sharedPreferences.getString(COMPANY_TELEPHONE, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_TELEPHONE, value).apply()

    var companyStreet : String?
        get() = sharedPreferences.getString(COMPANY_STREET, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_STREET, value).apply()

    var companyDistrict : String?
        get() = sharedPreferences.getString(COMPANY_DISTRICT, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_DISTRICT, value).apply()

    var companyNumber : String?
        get() = sharedPreferences.getString(COMPANY_NUMBER, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_NUMBER, value).apply()

    var companyCity : String?
        get() = sharedPreferences.getString(COMPANY_CITY, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_CITY, value).apply()

    var companyAddressContinued : String?
        get() = sharedPreferences.getString(COMPANY_ADDRESS_CONTINUED, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(COMPANY_ADDRESS_CONTINUED, value).apply()

    var lastScreen: String?
        get() = sharedPreferences.getString(LAST_SCREEN, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(LAST_SCREEN, value).apply()

    var userRole: String?
        get() = sharedPreferences.getString(USER_ROLE, EMPTY_STRING)
        set(value) = sharedPreferences.edit().putString(USER_ROLE, value).apply()

    companion object{
        const val SHARED_PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}.ENCRYPT_SHARED_PREFERENCES"
        private const val LAST_SCREEN = "$SHARED_PREFERENCES_NAME.LAST_SCREEN"
        private const val USER_ID = "$SHARED_PREFERENCES_NAME.USER_ID"
        private const val USER_EMAIL = "$SHARED_PREFERENCES_NAME.USER_EMAIL"
        private const val USER_PASSWORD = "$SHARED_PREFERENCES_NAME.USER_PASSWORD"
        private const val USER_NAME = "$SHARED_PREFERENCES_NAME.USER_NAME"
        private const val USER_DOCUMENT = "$SHARED_PREFERENCES_NAME.USER_DOCUMENT"
        private const val USER_CELLPHONE = "$SHARED_PREFERENCES_NAME.USER_CELLPHONE"
        private const val USER_BIRTHDAY = "$SHARED_PREFERENCES_NAME.USER_BIRTHDAY"
        private const val COMPANY_ID = "$SHARED_PREFERENCES_NAME.COMPANY_ID"
        private const val COMPANY_NAME = "$SHARED_PREFERENCES_NAME.COMPANY_NAME"
        private const val COMPANY_CATEGORY = "$SHARED_PREFERENCES_NAME.COMPANY_CATEGORY"
        private const val COMPANY_DOCUMENT = "$SHARED_PREFERENCES_NAME.COMPANY_DOCUMENT"
        private const val COMPANY_OWNER = "$SHARED_PREFERENCES_NAME.COMPANY_OWNER"
        private const val COMPANY_STATE = "$SHARED_PREFERENCES_NAME.COMPANY_STATE"
        private const val COMPANY_ZIP_CODE = "$SHARED_PREFERENCES_NAME.COMPANY_ZIP_CODE"
        private const val COMPANY_TELEPHONE = "$SHARED_PREFERENCES_NAME.COMPANY_TELEPHONE"
        private const val COMPANY_STREET = "$SHARED_PREFERENCES_NAME.COMPANY_STREET"
        private const val COMPANY_DISTRICT = "$SHARED_PREFERENCES_NAME.COMPANY_DISTRICT"
        private const val COMPANY_NUMBER = "$SHARED_PREFERENCES_NAME.COMPANY_NUMBER"
        private const val COMPANY_CITY = "$SHARED_PREFERENCES_NAME.COMPANY_CITY"
        private const val COMPANY_ADDRESS_CONTINUED = "$SHARED_PREFERENCES_NAME.COMPANY_ADDRESS_CONTINUED"
        private const val USER_ROLE = "$SHARED_PREFERENCES_NAME.USER_ROLE"
        const val EMPTY_STRING = ""
    }
}