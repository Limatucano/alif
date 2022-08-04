package com.tcc.alif.data.api

import com.tcc.alif.data.model.Companies
import com.tcc.alif.data.model.Queues
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.model.SigninResponse
import retrofit2.Response
import retrofit2.http.*

interface AlifService {

    @POST("user/signin")
    suspend fun signin(
        @Body user : Signin
    ) : SigninResponse

    @POST("administrator/companies")
    suspend fun getAllCompanies(
        @Body idAdministrator : Map<String,String>
    ) : Companies

    @POST("administrator/queues")
    suspend fun getQueuesBy(
        @Body idCompany : Map<String,String>
    ) : Queues

}