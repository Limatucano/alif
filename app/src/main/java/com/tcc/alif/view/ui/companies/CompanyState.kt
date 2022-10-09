package com.tcc.alif.view.ui.companies

import com.tcc.alif.data.model.Companies

sealed class CompanyState{
    object CompanySaved : CompanyState()
    data class Success(val response : Companies) : CompanyState()
    data class Loading(val loading : Boolean) : CompanyState()
    data class Error(val message : String?) : CompanyState()
}

