package com.tcc.alif.view.ui.companies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.model.Companies
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentCompaniesBinding
import com.tcc.alif.view.adapter.CompaniesAdapter
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompaniesFragment : BaseFragment<FragmentCompaniesBinding>(FragmentCompaniesBinding::inflate) {

    private val viewModel : CompaniesViewModel by viewModels()
    private lateinit var user : SigninResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = CompaniesFragmentArgs.fromBundle(bundle)
        user = args.user

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.companies_title),
            navigationBack = false
        )
        if(user.isAdministrator != null){
            viewModel.handleIntent(CompanyIntent.GetAllCompanies(user.uid))
        }
        setObservers()
        setListeners()
        setViews()

    }

    private fun setViews() = binding.run {
        rvCompanies.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )

    }

    private fun setListeners() = binding.run{
        addCompany.setOnClickListener {
            val direction = CompaniesFragmentDirections.actionCompaniesFragmentToCompanyFormFragment()
            requireView().findNavController().navigate(direction)
        }
    }

    private fun companySelected(companyResponse : CompanyResponse){
        val direction = CompaniesFragmentDirections.actionCompaniesFragmentToMainAdministratorFragment(companyResponse)
        requireView().findNavController().navigate(direction)
    }

    private fun setAdapter(companies: Companies) = binding.run{
        rvCompanies.adapter = CompaniesAdapter(requireContext(),companies) { company -> companySelected(company) }
    }

    private fun setObservers(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is CompanyState.Success -> {
                    setAdapter(state.response)
                    updateLoading(false)
                }
                is CompanyState.Error -> Toast.makeText(requireContext(), "Não foi", Toast.LENGTH_SHORT).show()
                is CompanyState.Loading -> updateLoading(state.loading)
            }
        }
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        companiesSwipe.isEnabled = loading
        companiesSwipe.isRefreshing = loading
    }

}