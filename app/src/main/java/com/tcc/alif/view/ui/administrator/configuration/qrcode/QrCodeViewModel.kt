package com.tcc.alif.view.ui.administrator.configuration.qrcode

import android.content.Context
import android.graphics.Color
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tcc.alif.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    val state = MutableLiveData<QrCodeState>()

    fun handleIntent(intent: QrCodeIntent){
        when(intent){
            is QrCodeIntent.GenerateQrCode -> generateQrCode(intent.idCompany)
        }
    }

    private fun generateQrCode(idCompany: String){
        val encoder = QRGEncoder(
            idCompany,
            null,
            QRGContents.Type.TEXT,
            QR_CODE_SIZE
        )
        encoder.colorBlack = Color.TRANSPARENT
        encoder.colorWhite = context.resources.getColor(R.color.pink, null)
        state.postValue(
            QrCodeState.QrCodeLoaded(
                qrCode = encoder.bitmap
            )
        )
    }

    companion object{
        const val QR_CODE_SIZE = 2000
    }
}