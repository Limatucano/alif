package com.tcc.alif.view.ui.administrator.configuration.qrcode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.databinding.FragmentQrcodeBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrCodeFragment : BaseFragment<FragmentQrcodeBinding>(FragmentQrcodeBinding::inflate) {

    private lateinit var company: CompanyResponse
    private val viewModel : QrCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = QrCodeFragmentArgs.fromBundle(bundle)
        company = args.company
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.qrcode_page)
        )
        setObserver()

        company.idCompany?.let { idCompany ->
            viewModel.handleIntent(QrCodeIntent.GenerateQrCode(
                idCompany = idCompany
            ))
        }
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is QrCodeState.QrCodeLoaded -> binding.qrCode.setImageBitmap(state.qrCode)
            }
        }
    }
}