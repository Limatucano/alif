package com.tcc.alif.view.ui.administrator.configuration.qrcode

import android.app.Activity
import android.graphics.Bitmap

sealed class QrCodeIntent{
    data class GenerateQrCode(val idCompany: String) : QrCodeIntent()
    data class PrintQrCode(
        val qrCode: Bitmap,
        val activity: Activity
    ) : QrCodeIntent()
    data class ShareQrCode(
        val qrCode: Bitmap,
        val activity: Activity
    ) : QrCodeIntent()
}
