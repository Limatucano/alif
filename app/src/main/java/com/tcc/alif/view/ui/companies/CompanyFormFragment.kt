package com.tcc.alif.view.ui.companies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentCompanyFormBinding
import com.tcc.alif.view.ui.BaseFragment


class CompanyFormFragment : BaseFragment<FragmentCompanyFormBinding>(FragmentCompanyFormBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.company_form_title),
            navigationBack = true
        )
        setListener()
    }

    private fun setListener() = binding.apply{
        saveCompany.setOnClickListener {
            //TODO: Implement request when endpoint is ready.
        }
    }

}