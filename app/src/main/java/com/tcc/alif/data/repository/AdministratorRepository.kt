package com.tcc.alif.data.repository

import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.model.Calls
import com.tcc.alif.data.model.Companies
import com.tcc.alif.data.model.Queues
import javax.inject.Inject

interface AdministratorRepository{

    suspend fun getQueuesBy(idCompany : String) : Queues
    suspend fun getAllCompanies(idAdministrator : Map<String,String>) : Companies
    suspend fun getCallsBy(idQueue : Int) : Calls
}

class AdministratorRepositoryImpl @Inject constructor(
    private val service: AlifService
) : AdministratorRepository {

    override suspend fun getAllCompanies(idAdministrator: Map<String,String>) : Companies = service.getAllCompanies(idAdministrator)

    override suspend fun getCallsBy(idQueue: Int): Calls = service.getCallsBy(idQueue)

    override suspend fun getQueuesBy(idCompany: String): Queues {
        return service.getQueuesBy(
            mapOf(
                "idCompany" to idCompany
            )
        )
    }

}