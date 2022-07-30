package com.tcc.alif.view.ui.companies


sealed class CompanyIntent {
    data class getAllCompanies(val idAdministrator: String) : CompanyIntent()
}