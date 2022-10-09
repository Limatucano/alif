package com.tcc.alif.view.ui.companies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.util.ValidateUtil.generateUUID
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.data.util.validateFields
import com.tcc.alif.databinding.FragmentCompanyFormBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyFormFragment : BaseFragment<FragmentCompanyFormBinding>(FragmentCompanyFormBinding::inflate) {

    private val viewModel : CompaniesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.company_form_title),
            navigationBack = true
        )
        setListener()
        setObservers()
    }

    //TODO: Implement save user in sharedPreferences to get user in other sections
    private fun setListener() = binding.apply{
        saveCompany.setOnClickListener {
            if(getAllFields().validateFields()){
                viewModel.handleIntent(
                    intent = CompanyIntent.SaveNewCompany(
                        company = generateModel(),
                        idUser = "d0FzOUlnhpX9rdI32XHxAwvL5q93"
                    )
                )
            }
        }
    }

    private fun setObservers(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is CompanyState.CompanySaved -> {
                    updateLoading(false)
                    goToCompanies()
                }
                is CompanyState.Error -> Toast.makeText(requireContext(), "Não foi", Toast.LENGTH_SHORT).show()
                is CompanyState.Loading -> updateLoading(state.loading)
            }
        }
    }

    private fun goToCompanies(){
        requireView().findNavController().popBackStack()
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        companySwipe.isEnabled = loading
        companySwipe.isRefreshing = loading
    }

    //TODO: Improve form - uf field as select, implement zipcode query to complete other fields
    //TODO: Add category and cnpj fields to form
    private fun generateModel() =
        CompanyResponse(
            idCompany = generateUUID(),
            category = "Barbearia",
            tradeName = binding.tradeNameEt.text.toString().emptyIfNull(),
            ownerName = binding.ownerNameEt.text.toString().emptyIfNull(),
            telephone = binding.telephoneEt.text.toString().emptyIfNull(),
            street = binding.streetEt.text.toString().emptyIfNull(),
            district = binding.districtEt.text.toString().emptyIfNull(),
            numberHouse = binding.numberEt.text.toString().emptyIfNull(),
            city = binding.cityEt.text.toString().emptyIfNull(),
            zipCode = binding.zipEt.text.toString().emptyIfNull(),
            state = binding.ufEt.text.toString().emptyIfNull(),
            addressContinued = binding.addressContinuedEt.text.toString().emptyIfNull(),
            cnpj = "59463657000138"
        )

    private fun getAllFields() =
        listOf(
            binding.tradeNameEt,
            binding.ownerNameEt,
            binding.telephoneEt,
            binding.streetEt,
            binding.districtEt,
            binding.numberEt,
            binding.cityEt,
            binding.zipEt,
            binding.ufEt,
            binding.addressContinuedEt
        )
}