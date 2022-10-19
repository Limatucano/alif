package com.tcc.alif.data.api

import com.tcc.alif.data.model.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {

    @GET("{cep}/json/")
    suspend fun getAddress(
        @Path("cep") cep : String
    ) : AddressResponse
}