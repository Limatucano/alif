package com.tcc.alif.model.api

import com.tcc.alif.model.ClientInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AlifService {

    //Cadastrar usuario
    @Headers("Content-Type: application/json")
    @POST("cliente/cadastro")
    fun registerClient(
        @Body userData: ClientInfo
    ): Call<ClientInfo>
}