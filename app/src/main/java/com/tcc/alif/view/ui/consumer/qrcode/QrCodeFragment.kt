package com.tcc.alif.view.ui.consumer.qrcode

import android.Manifest
import android.os.Bundle
import android.view.View
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.tcc.alif.data.util.PermissionHelper
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

    //TODO: implement view in camera
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(
            title = ""
        )
        setView()
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
                it.text?.let {

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
        //TODO: Implement modal to go to settings
    }
}