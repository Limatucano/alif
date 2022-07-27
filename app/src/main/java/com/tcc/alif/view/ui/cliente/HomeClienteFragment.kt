package com.tcc.alif.view.ui.cliente

import android.os.Bundle
import android.view.View
import com.tcc.alif.data.domain.MinhasFilasData
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.databinding.FragmentFirstClienteBinding
import com.tcc.alif.view.adapter.MinhasFilasAdapter
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeClienteFragment : BaseFragment<FragmentFirstClienteBinding>(FragmentFirstClienteBinding::inflate), MinhasFilasAdapter.OnClickItemListener {

    private lateinit var user : SigninResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = HomeClienteFragmentArgs.fromBundle(bundle)
        user = args.user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


    override fun onItemClick(items: MinhasFilasData, position: Int) {

    }
}