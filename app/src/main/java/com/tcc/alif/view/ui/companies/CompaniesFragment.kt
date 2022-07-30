package com.tcc.alif.view.ui.companies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tcc.alif.R
import com.tcc.alif.data.model.Companies
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.SigninResponse
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

        setupToolbar(binding.toolbar,getString(R.string.companies_title), false)
        setObservers()
        setListeners()
        binding.rvCompanies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.rvCompanies.addItemDecoration(DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL))
        if(user.idAdministrator != null){
            viewModel.handleIntent(CompanyIntent.getAllCompanies(user.idAdministrator!!))
        }
    }

    private fun setListeners() = binding.run{
        addCompany.setOnClickListener {
            Toast.makeText(requireContext(), "clicaste?", Toast.LENGTH_SHORT).show()
        }
    }

    private fun companySelected(company : CompanyResponse){
        Toast.makeText(requireContext(), "selecionou essa pq?", Toast.LENGTH_SHORT).show()
    }

    private fun setAdapter(companies: Companies) = binding.run{
        rvCompanies.adapter = CompaniesAdapter(requireContext(),companies) { company -> companySelected(company) }
    }

    private fun setObservers(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is CompanyState.Success -> setAdapter(state.response)
                is CompanyState.Error -> Toast.makeText(requireContext(), "Não foi", Toast.LENGTH_SHORT).show()
                is CompanyState.Loading -> Toast.makeText(requireContext(), "ta indo", Toast.LENGTH_SHORT).show()
            }
        }
    }

}