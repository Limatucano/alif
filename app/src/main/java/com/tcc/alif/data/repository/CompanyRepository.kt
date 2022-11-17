package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.CompanyDataSource
import com.tcc.alif.data.model.CompanyResponse
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val dataSource: CompanyDataSource,
) {

    fun getAllCompaniesByUser(idUser: String) = dataSource.getAllCompaniesByUser(idUser = idUser)

    fun saveNewCompany(
        company: CompanyResponse,
        idUser: String
    ) = dataSource.saveNewCompany(
        company = company,
        idUser = idUser
    )

    fun updateCompany(
        company: CompanyResponse,
        idCompany: String
    ) = dataSource.updateCompany(
        company = company,
        idCompany = idCompany
    )

    suspend fun getAddress(cep: String) = dataSource.getAddress(cep)

}