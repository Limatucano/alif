package com.tcc.alif.view.ui.companies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.CompaniesRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val companiesRepository: CompaniesRepository
) : ViewModel() {

    val state = MutableLiveData<CompanyState>()

    fun handleIntent(intent : CompanyIntent){
        when(intent){
            is CompanyIntent.getAllCompanies -> getAllCompanies(intent.idAdministrator)
        }
    }

    private fun getAllCompanies(idAdministrator : String){
        val body = mapOf(
            ADMINISTRATOR_ID_REQUEST to idAdministrator
        )
        viewModelScope.request(
                blockToRun = { companiesRepository.getAllCompanies(body)},
                onSuccess = { state.postValue(CompanyState.Success(it)) },
                onLoading = { state.postValue(CompanyState.Loading(it)) },
                onError = {state.postValue(CompanyState.Error(it))}
            )

    }

    companion object{
        const val ADMINISTRATOR_ID_REQUEST = "idAdministrator"
    }

}