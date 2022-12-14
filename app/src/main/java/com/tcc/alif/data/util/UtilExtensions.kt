package com.tcc.alif.data.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.tcc.alif.R
import com.tcc.alif.view.ui.administrator.configuration.qrcode.QrCodeState
import com.tcc.alif.view.ui.administrator.configuration.qrcode.QrCodeViewModel

fun Context.getEncryptedSharedPreferences(name: String) : SharedPreferences{
    return EncryptedSharedPreferences.create(
        name,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        this,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    })
}

fun Context.generateQrCode(
    backgroundColor: Int = Color.TRANSPARENT,
    lineColor: Int = this.resources.getColor(R.color.pink, null),
    value: String
) : Bitmap {
    val encoder = QRGEncoder(
        value,
        null,
        QRGContents.Type.TEXT,
        QrCodeViewModel.QR_CODE_SIZE
    )
    encoder.colorBlack = backgroundColor
    encoder.colorWhite = lineColor
    return encoder.bitmap
}

fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toDp() = (this / Resources.getSystem().displayMetrics.density).toInt()