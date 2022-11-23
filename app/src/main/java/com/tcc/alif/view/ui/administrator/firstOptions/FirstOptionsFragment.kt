package com.tcc.alif.view.ui.administrator.firstOptions

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentFirstOptionsBinding
import com.tcc.alif.view.ui.BaseFragment

class FirstOptionsFragment : BaseFragment<FragmentFirstOptionsBinding>(FragmentFirstOptionsBinding::inflate) {

    private val args : FirstOptionsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.first_options_title),
            navigationBack = false
        )
        setListener()
    }

    private fun setListener() = binding.run {
        companiesOption.setOnClickListener {
            requireView()
                .findNavController()
                .navigate(
                    FirstOptionsFragmentDirections.actionFirstOptionsFragmentToCompaniesFragment(args.user)
                )
        }
        notificationsOption.setOnClickListener {
            requireView()
                .findNavController()
                .navigate(
                    FirstOptionsFragmentDirections.actionFirstOptionsFragmentToBusinessRequestsFragment()
                )
        }
    }
}