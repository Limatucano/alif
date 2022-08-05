package com.tcc.alif.data.api

import com.tcc.alif.data.model.*
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

    @GET("administrator/calls/")
    suspend fun getCallsBy(
        @Query("idQueue") idQueue : Int
    ) : Calls

}