package com.tcc.alif.data.repository

import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.model.Companies
import retrofit2.Response
import javax.inject.Inject

interface CompaniesRepository{
    suspend fun getAllCompanies(idAdministrator : Map<String,String>) : Companies
}

class CompaniesRepositoryImpl @Inject constructor(
    val alifService: AlifService
    ) : CompaniesRepository{

    override suspend fun getAllCompanies(idAdministrator: Map<String,String>) : Companies = alifService.getAllCompanies(idAdministrator)


}