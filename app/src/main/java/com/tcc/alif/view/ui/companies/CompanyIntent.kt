package com.tcc.alif.view.ui.companies

import com.tcc.alif.data.model.CompanyResponse


sealed class CompanyIntent {
    data class GetAllCompanies(val idUser: String) : CompanyIntent()
    data class SaveNewCompany(
        val company: CompanyResponse,
        val idUser: String
    ) : CompanyIntent()
    data class UpdateCompany(
        val company: CompanyResponse,
        val idCompany: String
    ) : CompanyIntent()
    data class GetAddress(val cep: String) : CompanyIntent()
}