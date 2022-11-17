package com.tcc.alif.view.ui.companies

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.model.AddressResponse
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.local.getAllCategories
import com.tcc.alif.data.model.local.getAllStates
import com.tcc.alif.data.util.MaskUtils.setCellphoneMask
import com.tcc.alif.data.util.MaskUtils.setCnpjMask
import com.tcc.alif.data.util.MaskUtils.setZipCodeMask
import com.tcc.alif.data.util.ValidateUtil.generateUUID
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.databinding.FragmentCompanyFormBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyFormFragment : BaseFragment<FragmentCompanyFormBinding>(FragmentCompanyFormBinding::inflate) {

    private val viewModel : CompaniesViewModel by viewModels()
    private var company: CompanyResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = CompanyFormFragmentArgs.fromBundle(bundle)
        company = args.company
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.company_form_title),
            navigationBack = true
        )
        setViews()
        setListener()
        setObservers()
        if(company != null){
            setViewsToEdit()
        }else{
            getAddress()
        }
    }

    private fun setViewsToEdit() = binding.run {
        categoryAc.setText(company?.category.toString().emptyIfNull())
        tradeNameEt.setText(company?.tradeName.toString().emptyIfNull())
        ownerNameEt.setText(company?.ownerName.toString().emptyIfNull())
        telephoneEt.setText(company?.telephone.toString().emptyIfNull())
        streetEt.setText(company?.street.toString().emptyIfNull())
        districtEt.setText(company?.district.toString().emptyIfNull())
        numberEt.setText(company?.numberHouse.toString().emptyIfNull())
        cityEt.setText(company?.city.toString().emptyIfNull())
        zipEt.setText(company?.zipCode.toString().emptyIfNull())
        ufAc.setText(company?.state.toString().emptyIfNull())
        addressContinuedEt.setText(company?.addressContinued.toString().emptyIfNull())
        cnpjEt.setText(company?.cnpj.toString().emptyIfNull())
    }

    private fun setViews() = binding.apply {
        telephoneEt.addTextChangedListener(telephoneEt.setCellphoneMask())
        cnpjEt.addTextChangedListener(cnpjEt.setCnpjMask())
        zipEt.addTextChangedListener(zipEt.setZipCodeMask())

        val statesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            getAllStates()
        )
        ufAc.setAdapter(statesAdapter)

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
            if(company == null){
                viewModel.handleIntent(
                    intent = CompanyIntent.SaveNewCompany(
                        company = generateModel(),
                        idUser = sharedPreferences.userId ?: EMPTY_STRING
                    )
                )
            } else {
                viewModel.handleIntent(
                    intent = CompanyIntent.UpdateCompany(
                        company = generateModel(),
                        idCompany = sharedPreferences.companyId ?: EMPTY_STRING
                    )
                )
            }
        }


    }

    private fun getAddress() = binding.run{
        zipEt.addTextChangedListener {
            val zipCode = it.toString()
            if(zipCode.isNotEmpty() && zipCode.length > MIN_ZIP_TO_REQUEST){
                viewModel.handleIntent(
                    intent = CompanyIntent.GetAddress(
                        cep = zipCode
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
                is CompanyState.Error -> {
                    updateLoading(false)
                }
                is CompanyState.Loading -> updateLoading(state.loading)
                is CompanyState.Address -> {
                    updateLoading(false)
                    updateAddress(state.addressResponse)
                }
                is CompanyState.CompanyUpdated -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
            }
        }
    }

    private fun updateAddress(address: AddressResponse) = binding.run{
        streetEt.setText(address.publicPlace)
        districtEt.setText(address.district)
        cityEt.setText(address.city)
        ufAc.setText(address.uf, false)
    }

    private fun goToCompanies(){
        requireView().findNavController().popBackStack()
    }

    private fun updateLoading(loading : Boolean) = binding.run{
        companySwipe.isEnabled = loading
        companySwipe.isRefreshing = loading
    }

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
            state = binding.ufAc.editableText.toString().emptyIfNull(),
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
            binding.zipEt
        )

    companion object{
        const val MIN_ZIP_TO_REQUEST = 6
    }
}