package com.tcc.alif.data.repository

import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.model.Queues
import retrofit2.Response
import javax.inject.Inject

interface AdministratorRepository{

    suspend fun getQueuesBy(idCompany : String) : Queues
}

class AdministratorRepositoryImpl @Inject constructor(
    private val service: AlifService
) : AdministratorRepository {

    override suspend fun getQueuesBy(idCompany: String): Queues {
        return service.getQueuesBy(
            mapOf(
                "idCompany" to idCompany
            )
        )
    }

}