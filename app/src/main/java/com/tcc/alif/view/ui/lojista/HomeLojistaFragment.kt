package com.tcc.alif.view.ui.lojista

import android.os.Bundle
import android.view.View
import com.tcc.alif.databinding.FragmentHomeLojistaBinding
import com.tcc.alif.data.domain.MinhasFilasData
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.view.adapter.MinhasFilasHomeAdapter
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeLojistaFragment(val user: SigninResponse) : BaseFragment<FragmentHomeLojistaBinding>(FragmentHomeLojistaBinding::inflate), MinhasFilasHomeAdapter.OnClickItemListener {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val teste = user.name
    }

    private fun setViews() = binding.run{

    }
    override fun onItemClick(items: MinhasFilasData, position: Int)  {
    }
}