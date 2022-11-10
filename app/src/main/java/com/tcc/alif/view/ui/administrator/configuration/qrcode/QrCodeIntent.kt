package com.tcc.alif.view.ui.administrator.configuration.qrcode

sealed class QrCodeIntent{
    data class GenerateQrCode(val idCompany: String) : QrCodeIntent()
}
