package com.tcc.alif.model.api

import com.tcc.alif.model.*
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

    //Cadastra lojista
    @Headers("Content-Type: application/json")
    @POST("lojista/cadastro")
    fun registerLojista(
        @Body userData: LojistaInfo
    ):Call<LojistaInfo>

    //Realiza login lojista
    @Headers("Content-Type: application/json")
    @POST("lojista/login")
    fun loginLojista(
        @Body userData: LojistaInfo
    ):Call<LojistaInfo>


    //Realiza login cliente
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

    //Filtra as filas pelo nome
    @Headers("Content-Type: application/json")
    @POST("fila/nome")
    fun getFilasByName(
            @Body userData: MinhasFilasResponse
    ): Call<MinhasFilas>
}