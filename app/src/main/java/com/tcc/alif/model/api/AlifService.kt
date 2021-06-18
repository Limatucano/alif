package com.tcc.alif.model.api

import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.MinhasFilas
import com.tcc.alif.model.MinhasFilasPost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AlifService {

    //Cadastrar usuario
    @Headers("Content-Type: application/json")
    @POST("cliente/cadastro")
    fun registerClient(
        @Body userData: ClientInfo
    ): Call<ClientInfo>

    //Verificar email
    @Headers("Content-Type: application/json")
    @POST("cliente/verificar")
    fun verifyEmail(
        @Body userData: ClientInfo
    ): Call<ClientInfo>

    //Realizar login
    @Headers("Content-Type: application/json")
    @POST("cliente/login")
    fun login(
            @Body userData: ClientInfo
    ): Call<ClientInfo>

    //Pega todas filas do usuário
    @Headers("Content-Type: application/json")
    @POST("cliente/minhasfilas")
    fun getMyFilas(
            @Body userData: MinhasFilasPost
    ): Call<MinhasFilas>
}