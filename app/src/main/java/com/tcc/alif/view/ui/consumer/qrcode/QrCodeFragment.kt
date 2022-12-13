package com.tcc.alif.view.ui.consumer.qrcode

import android.Manifest
import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.navigation.findNavController
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.tcc.alif.data.util.PermissionHelper
import com.tcc.alif.data.util.openAppSystemSettings
import com.tcc.alif.databinding.FragmentQrcodeAbstractBinding
import com.tcc.alif.view.ui.BaseFragment

class QrCodeFragment :
    BaseFragment<FragmentQrcodeAbstractBinding>(FragmentQrcodeAbstractBinding::inflate),
    PermissionHelper.OnPermissionResult
{

    private var isShowingScanner = false
    private val permissionHelper : PermissionHelper = PermissionHelper(
        fragment = this,
        permission = Manifest.permission.CAMERA,
        onPermissionResult = this
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(
            title = ""
        )
        setView()
        animateScanner()
    }

    private fun animateScanner() = binding.run {
        val path = Path().apply {
            rLineTo(
                LINE_ANIMATION_X,
                LINE_ANIMATION_Y
            )
        }
        ObjectAnimator.ofFloat(lineScanner, View.X, View.Y, path).apply {
            duration = LINE_ANIMATION_DURATION
            interpolator = LinearInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            if(!isStarted){
                start()
            }
        }
    }

    private fun setView() = binding.run {
        qrCodeView.decoderFactory = DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE))
    }

    override fun onStart() {
        super.onStart()
        permissionHelper.launch()
    }

    override fun onResume() {
        super.onResume()
        if(isShowingScanner) initScanner()
    }

    private fun initScanner() = binding.run {
        with(qrCodeView){
            decodeSingle {
                stopDecoding()
                it.text?.let { qrCode ->
                    findNavController()
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(
                            QR_CODE_KEY,
                            qrCode
                        )
                    findNavController().popBackStack()
                }
            }
            resume()
        }

    }

    override fun onDestroyView() {
        binding.qrCodeView.stopDecoding()
        super.onDestroyView()
    }

    override fun onAllPermissionsGranted() {
        isShowingScanner = true
    }

    override fun onPermissionsDenied(
        deniedPermissions: List<String>,
        deniedPermissionsWithNeverAskAgain: List<String>
    ) {
        requireContext().openAppSystemSettings()
    }

    companion object{
        private const val LINE_ANIMATION_DURATION = 4000L
        private const val LINE_ANIMATION_Y = 900F
        private const val LINE_ANIMATION_X = 0F
        const val QR_CODE_KEY = "QrCode"
    }
}