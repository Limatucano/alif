package com.tcc.alif.view.ui.administrator.configuration.qrcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.print.PrintHelper
import com.tcc.alif.data.util.generateQrCode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    val state = MutableLiveData<QrCodeState>()

    fun handleIntent(intent: QrCodeIntent){
        when(intent){
            is QrCodeIntent.GenerateQrCode -> generateQrCode(intent.value)
            is QrCodeIntent.PrintQrCode -> printQrCode(
                qrCode = intent.qrCode,
                activity = intent.activity
            )
            is QrCodeIntent.ShareQrCode -> shareQrCode(
                qrCode = intent.qrCode,
                activity = intent.activity
            )
        }
    }

    private fun shareQrCode(
        qrCode: Bitmap,
        activity: Activity
    ){
        with(activity){
            try {
                val uri = saveImage(
                    image = qrCode,
                    activity = activity
                )
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.type = "image/png"
                startActivity(intent)
            }catch (e: Exception){
                e.printStackTrace()
                state.postValue(
                    QrCodeState.Error(
                        message = ERROR_SHARE
                    )
                )
            }
        }
    }

    private fun saveImage(
        image: Bitmap,
        activity: Activity
    ) : Uri? {
        var uri: Uri?
        with(activity) {
            uri = try {
                val imagePath = File(cacheDir, "external_files")
                imagePath.mkdir()
                val imageFile = File(imagePath, "imageQrCode.png")
                val stream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.PNG, 90, stream)
                stream.flush()
                stream.close()
                FileProvider.getUriForFile(this, "$packageName.fileprovider", imageFile)
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
        }
        return uri
    }

    private fun printQrCode(qrCode: Bitmap, activity: Activity){
        activity.also { context ->
            try {
                val imagePrinter = PrintHelper(context).apply {
                    scaleMode = PrintHelper.SCALE_MODE_FIT
                    colorMode = PrintHelper.COLOR_MODE_MONOCHROME
                }
                imagePrinter.printBitmap("print Job", qrCode)
                state.postValue(QrCodeState.QrCodePrinted)
            }catch(e: Exception){
                e.printStackTrace()
                state.postValue(
                    QrCodeState.Error(
                        message = ERROR_PRINT
                    )
                )
            }
        }
    }

    private fun generateQrCode(value: String){
        state.postValue(
            QrCodeState.QrCodeLoaded(
                qrCode = context.generateQrCode(value = value)
            )
        )
    }

    companion object{
        const val QR_CODE_SIZE = 2000
        const val ERROR_PRINT = "Erro ao imprimir QrCode"
        const val ERROR_SHARE = "Erro ao compartilhar QrCode"
    }
}