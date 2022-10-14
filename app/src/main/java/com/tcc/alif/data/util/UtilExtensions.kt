package com.tcc.alif.data.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

fun Context.getEncryptedSharedPreferences(name: String) : SharedPreferences{
    return EncryptedSharedPreferences.create(
        name,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        this,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}