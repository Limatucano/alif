package com.tcc.alif.view.ui.administrator.configuration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
class ConfigurationFragment : BaseFragment<FragmentConfigurationBinding>(FragmentConfigurationBinding::inflate) {

    private val viewModel : ConfigurationViewModel by viewModels()

    private val optionsAdapter by lazy {
        OptionConfigurationAdapter(
            context = requireContext(),
            action = { openByIntent(it) }
        )
    }

    //TODO: Create rule in user, and show option by rule
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListener()
        setAdapterItems()
        setObserver()
        setupToolbar(
            title = getString(R.string.configuration_page)
        )
    }

    private fun setObserver() {
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is ConfigurationState.ExitedSuccessfully -> goToModeScreen()
            }
        }
    }

    private fun setListener() = binding.run {
        qrCode.setOnClickListener {
            val direction = MainAdministratorFragmentDirections.toQrCodePage(sharedPreferences.companyId ?: "")
            requireView().findNavController().navigate(direction)
        }
    }

    private fun setViews() = binding.run{
        userName.text = sharedPreferences.companyName
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
            is ConfigurationIntent.GoToCompanyProfile -> openCompanyProfile()
            is ConfigurationIntent.Exit -> exit(intent)
            is ConfigurationIntent.GoToMyBusinessRequests -> openMyBusinessRequests()
        }
    }

    private fun openMyBusinessRequests(){
        requireView()
            .findNavController()
            .navigate(
                MainAdministratorFragmentDirections.actionMainAdministratorFragmentToBusinessRequestsFragment()
            )
    }

    private fun exit(intent: ConfigurationIntent){
        viewModel.handleIntent(intent)
    }

    private fun goToModeScreen(){
        requireView()
            .findNavController()
            .navigate(
                MainAdministratorFragmentDirections.actionMainAdministratorFragmentToModeFragment()
            )
    }

    private fun openMyCategories(){
        requireView()
            .findNavController()
            .navigate(
                MainAdministratorFragmentDirections.actionMainAdministratorFragmentToMyCategoriesFragment()
            )
    }

    private fun openCompanyProfile(){
        requireView()
            .findNavController()
            .navigate(
                MainAdministratorFragmentDirections.actionMainAdministratorFragmentToCompanyFormFragment(
                    company = CompanyResponse(
                        idCompany = sharedPreferences.companyId,
                        category = sharedPreferences.companyCategory,
                        tradeName = sharedPreferences.companyName,
                        ownerName = sharedPreferences.companyOwner,
                        telephone = sharedPreferences.companyTelephone,
                        street = sharedPreferences.companyStreet,
                        district = sharedPreferences.companyDistrict,
                        numberHouse = sharedPreferences.companyNumber,
                        city = sharedPreferences.companyCity,
                        zipCode = sharedPreferences.companyZipCode,
                        state = sharedPreferences.companyState,
                        addressContinued = sharedPreferences.companyAddressContinued,
                        cnpj = sharedPreferences.companyDocument
                    )
                )
            )
    }

    private fun openProfile(){
        requireView()
            .findNavController()
            .navigate(
                MainAdministratorFragmentDirections.actionMainAdministratorFragmentToUserProfileFragment()
            )
    }

    private fun openChangePassword(){
        requireView()
            .findNavController()
            .navigate(
                MainAdministratorFragmentDirections.toChangePassword()
            )
    }

    private fun setAdapterItems(){
        optionsAdapter.options = ConfigurationOptions.values().toList()
    }
}
