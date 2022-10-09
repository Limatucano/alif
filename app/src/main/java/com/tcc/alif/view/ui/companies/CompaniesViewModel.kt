package com.tcc.alif.view.ui.companies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.Companies
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.repository.AdministratorRepository
import com.tcc.alif.data.repository.CompanyRepository
import com.tcc.alif.data.util.request
import com.tcc.alif.data.util.requestFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val repository: CompanyRepository
) : ViewModel() {

    val state = MutableLiveData<CompanyState>()

    fun handleIntent(intent : CompanyIntent){
        when(intent){
            is CompanyIntent.GetAllCompanies -> getAllCompanies(intent.idCompanies)
        }
    }

    private fun getAllCompanies(idCompanies : List<String>){
        repository.getAllCompaniesByUser(idCompanies).requestFirebase(
            blockToRun = viewModelScope,
            onSuccess = { response ->
                val companies = Companies(
                    companies = response.map {
                        CompanyResponse().toCompanyResponse(map = it.data)
                    }
                )
                state.postValue(CompanyState.Success(companies))
            },
            onLoading = { state.postValue(CompanyState.Loading(it)) },
            onError = { state.postValue(CompanyState.Error(it)) }
        )
    }
}