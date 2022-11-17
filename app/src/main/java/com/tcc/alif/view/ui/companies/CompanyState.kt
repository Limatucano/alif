package com.tcc.alif.view.ui.companies

import com.tcc.alif.data.model.AddressResponse
import com.tcc.alif.data.model.Companies

sealed class CompanyState{
    object CompanySaved : CompanyState()
    data class CompanyUpdated(val message: String?) : CompanyState()
    data class Address(val addressResponse: AddressResponse) : CompanyState()
    data class Success(val response : Companies) : CompanyState()
    data class Loading(val loading : Boolean) : CompanyState()
    data class Error(val message : String?) : CompanyState()
}

