package com.tcc.alif.view.ui.companies

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.local.getAllCategories
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
        setViews()
        setListener()
        setObservers()
    }

    private fun setViews() = binding.apply {
        val titlesCategories = getAllCategories().map {
            requireContext().getString(it)
        }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            titlesCategories
        )
        categoryAc.setAdapter(adapter)
    }

    private fun setListener() = binding.apply{
        saveCompany.setOnClickListener {
            if(getRequiredFields().validateFields()){
                viewModel.handleIntent(
                    intent = CompanyIntent.SaveNewCompany(
                        company = generateModel(),
                        idUser = sharedPreferences.userId ?: EMPTY_STRING
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
                else -> throw IllegalArgumentException("State not mapped")
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

    //TODO: Improve form - uf field as select, implement zipcode query(https://viacep.com.br/ws/01153-000/json/) to complete other fields
    //TODO: Add mask to zipcode, telephone and cnpj

    private fun generateModel() =
        CompanyResponse(
            idCompany = generateUUID(),
            category = binding.categoryAc.editableText.toString(),
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
            cnpj = binding.cnpjEt.text.toString().emptyIfNull()
        )

    private fun getRequiredFields() =
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
        )
}