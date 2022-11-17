package com.tcc.alif.view.ui.companies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.Companies
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.repository.CompanyRepository
import com.tcc.alif.data.util.request
import com.tcc.alif.view.ui.administrator.configuration.mycategories.MyCategoriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val repository: CompanyRepository
) : ViewModel() {

    val state = MutableLiveData<CompanyState>()

    fun handleIntent(intent : CompanyIntent){
        when(intent){
            is CompanyIntent.GetAllCompanies -> getAllCompanies(intent.idUser)
            is CompanyIntent.SaveNewCompany -> saveNewCompany(
                company = intent.company,
                idUser = intent.idUser
            )
            is CompanyIntent.GetAddress -> getAddress(intent.cep)
            is CompanyIntent.UpdateCompany -> updateCompany(
                company = intent.company,
                idCompany = intent.idCompany
            )
        }
    }

    private fun getAddress(cep: String){
        viewModelScope.request(
            blockToRun = { repository.getAddress(cep) },
            onSuccess = { state.postValue(CompanyState.Address(it)) },
            onLoading = { state.postValue(CompanyState.Loading(it)) },
            onError = { state.postValue(CompanyState.Error(it)) }
        )
    }

    private fun saveNewCompany(
        company: CompanyResponse,
        idUser: String
    ){
        viewModelScope.launch {
            repository.saveNewCompany(
                company = company,
                idUser = idUser
            ).collect{ response ->
                when(response){
                    is Response.Loading -> state.postValue(CompanyState.Loading(response.loading))
                    is Response.Error -> state.postValue(CompanyState.Error(response.message))
                    is Response.Success -> state.postValue(CompanyState.CompanySaved)
                }
            }
        }
    }

    private fun updateCompany(
        company: CompanyResponse,
        idCompany: String
    ){
        viewModelScope.launch {
            repository.updateCompany(
                company = company,
                idCompany = idCompany
            ).request(
                blockToRun = this,
                onError = { state.postValue(CompanyState.Error(it)) },
                onLoading = { state.postValue(CompanyState.Loading(it)) },
                onSuccess = { state.postValue(CompanyState.CompanyUpdated(it)) }
            )
        }
    }

    private fun getAllCompanies(idUser : String){
        viewModelScope.launch {
            repository.getAllCompaniesByUser(idUser = idUser).collect{ response ->
                when(response){
                    is Response.Loading -> state.postValue(CompanyState.Loading(response.loading))
                    is Response.Error -> state.postValue(CompanyState.Error(response.message))
                    is Response.Success -> {
                        val companies = Companies(
                            companies = response.data.map {
                                CompanyResponse().toCompanyResponse(map = it.data)
                            }
                        )
                        state.postValue(CompanyState.Success(companies))
                    }
                }
            }
        }
    }
}