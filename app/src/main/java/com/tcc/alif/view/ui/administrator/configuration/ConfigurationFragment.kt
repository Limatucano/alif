package com.tcc.alif.view.ui.administrator.configuration

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.local.ConfigurationOptions
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentConfigurationBinding
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.MainAdministratorFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfigurationFragment(
    private val company : CompanyResponse
) : BaseFragment<FragmentConfigurationBinding>(FragmentConfigurationBinding::inflate) {

    private val optionsAdapter by lazy {
        OptionConfigurationAdapter(
            context = requireContext(),
            action = { openByIntent(it) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListener()
        setAdapterItems()
        setupToolbar(
            title = getString(R.string.configuration_page)
        )
    }

    private fun setListener() = binding.run {
        qrCode.setOnClickListener {
            val direction = MainAdministratorFragmentDirections.toQrCodePage(company)
            requireView().findNavController().navigate(direction)
        }
    }

    private fun setViews() = binding.run{
        userName.text = company.tradeName
        rvOptionsConfiguration.adapter = optionsAdapter
        rvOptionsConfiguration.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )
    }

    private fun openByIntent(intent: ConfigurationIntent){
        when(intent){
            is ConfigurationIntent.GoToChangePassword -> openChangePassword()
            is ConfigurationIntent.GoToProfile -> openProfile()
            is ConfigurationIntent.GoToMyCategories -> openMyCategories()
        }
    }

    private fun openMyCategories(){
        //TODO: Implement navigation to myCategories screen
    }

    private fun openProfile(){
        //TODO: Implement navigation to profile screen
    }

    private fun openChangePassword(){
        val directions = MainAdministratorFragmentDirections.toChangePassword()
        requireView().findNavController().navigate(directions)
    }

    private fun setAdapterItems(){
        optionsAdapter.options = ConfigurationOptions.values().toList()
    }
}
