package com.tcc.alif.view.ui.companies


sealed class CompanyIntent {
    data class GetAllCompanies(val idCompanies: List<String>) : CompanyIntent()
}