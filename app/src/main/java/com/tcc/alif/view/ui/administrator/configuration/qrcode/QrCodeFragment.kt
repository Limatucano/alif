package com.tcc.alif.view.ui.administrator.configuration.qrcode

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentQrcodeBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrCodeFragment : BaseFragment<FragmentQrcodeBinding>(FragmentQrcodeBinding::inflate) {

    private val args: QrCodeFragmentArgs by navArgs()
    private val viewModel : QrCodeViewModel by viewModels()
    private var qrCodeImage : Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.qrcode_page)
        )
        setObserver()
        setListener()
        viewModel.handleIntent(QrCodeIntent.GenerateQrCode(
            value = args.value
        ))
    }

    private fun setListener() = binding.run{
        print.setOnClickListener {
            if(qrCodeImage != null){
                viewModel.handleIntent(
                    QrCodeIntent.PrintQrCode(
                        qrCode = qrCodeImage!!,
                        activity = requireActivity()
                    )
                )
            }
        }

        share.setOnClickListener {
            if(qrCodeImage != null){
                viewModel.handleIntent(
                    QrCodeIntent.ShareQrCode(
                        qrCode = qrCodeImage!!,
                        activity = requireActivity()
                    )
                )
            }
        }
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is QrCodeState.QrCodeLoaded -> {
                    qrCodeImage = state.qrCode
                    binding.qrCode.setImageBitmap(state.qrCode)
                }
                is QrCodeState.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                is QrCodeState.QrCodePrinted, QrCodeState.QrCodeShared -> {}
            }
        }
    }
}