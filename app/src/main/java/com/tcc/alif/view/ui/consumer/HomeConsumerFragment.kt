package com.tcc.alif.view.ui.consumer

import android.os.Bundle
import android.view.View
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.databinding.FragmentFirstClienteBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeConsumerFragment : BaseFragment<FragmentFirstClienteBinding>(FragmentFirstClienteBinding::inflate){

    private lateinit var user : SigninResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = HomeConsumerFragmentArgs.fromBundle(bundle)
        user = args.user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}