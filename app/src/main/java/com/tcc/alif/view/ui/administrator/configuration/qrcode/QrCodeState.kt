package com.tcc.alif.view.ui.administrator.configuration.qrcode

import android.graphics.Bitmap

sealed class QrCodeState{
    data class QrCodeLoaded(val qrCode: Bitmap) : QrCodeState()
}
