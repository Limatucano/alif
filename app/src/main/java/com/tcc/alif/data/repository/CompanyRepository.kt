package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.CompanyDataSource
import com.tcc.alif.data.model.CompanyResponse
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val dataSource: CompanyDataSource,
) {

    fun getAllCompaniesByUser(idUser: String) = dataSource.getAllCompaniesByUser(idUser = idUser)

    fun saveNewCompany(company: CompanyResponse, idUser: String) = dataSource.saveNewCompany(
        company = company,
        idUser = idUser
    )

    suspend fun getAddress(cep: String) = dataSource.getAddress(cep)

}