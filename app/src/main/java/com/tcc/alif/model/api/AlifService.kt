package com.tcc.alif.model.api

import com.tcc.alif.model.*
import retrofit2.Call
import retrofit2.http.*

interface AlifService {
    @Headers("Content-type: application/json")
    @POST("fila/detailfila")
    fun getFilaById(
        @Body filaData : FilaInfo
    ): Call<MinhasFilas>

    @Headers("Content-type: application/json")
    @GET("cliente/data/{email}")
    fun getClientData(
        @Path("email") email: String
    ): Call<ClientInfo>

    @Headers("Content-Type: application/json")
    @PATCH("lojista/atualizarperfil")
    fun updateProfileLojista(
            @Body lojistaInfo : LojistaInfo
    ):Call<MessageRequest>

    @Headers("Content-Type: application/json")
    @GET("lojista/meusclientes/{id_fila}")
    fun getMeusClientesFila(
            @Path("id_fila") id_fila : String
    ):Call<MeusClientesFila>

    @Headers("Content-Type: application/json")
    @POST("lojista/meusprimeirosclientes")
    fun meusprimeirosclientes(
            @Body lojistaInfo : LojistaInfo
    ):Call<MeusPrimeirosClientes>

    @Headers("Content-Type: application/json")
    @PATCH("funcionario")
    fun updateFuncionario(
            @Body funcionario: FuncionarioInfo
    ):Call<MessageRequest>

    @Headers("Content-type: application/json")
    @DELETE("funcionario/{cod_funcionario}")
    fun deleteFuncionario(
            @Path("cod_funcionario") cod_funcionario: String
    ): Call<MessageRequest>

    @Headers("Content-type: application/json")
    @GET("lojista/{id_lojista}")
    fun getMyFuncionarios(
        @Path("id_lojista") id_lojista : String
    ): Call<MeusFuncionarios>

    @Headers("Content-type: application/json")
    @POST("funcionario")
    fun insertNewFuncionario(
        @Body funcionario: FuncionarioInfo
    ):Call<MessageRequest>

    //Atualizando fila
    @Headers("Content-Type: application/json")
    @PATCH("fila")
    fun updateFila(
            @Body filaData : FilaInfo
    ): Call<MessageRequest>

    //Deletar fila
    @Headers("Content-Type: application/json")
    @DELETE("fila/{id_fila}")
    fun deleteFila(
        @Path("id_fila") id_fila: String
    ): Call<MessageRequest>

    //Pegar filas do lojista
    @Headers("Content-Type: application/json")
    @POST("lojista/minhasfilas")
    fun getMyFilasLojista(
            @Body lojistaData: LojistaInfo
    ): Call<MinhasFilas>

    //Cadastrar usuario
    @Headers("Content-Type: application/json")
    @POST("lojista/data")
    fun getLojistaData(
        @Body lojistaData: LojistaInfo
    ): Call<LojistaInfo>

    //Cadastrar usuario
    @Headers("Content-Type: application/json")
    @POST("fila")
    fun registerFila(
        @Body filaData: FilaInfo
    ): Call<FilaInfo>

    //Cadastrar usuario
    @Headers("Content-Type: application/json")
    @POST("cliente/cadastro")
    fun registerClient(
        @Body userData: ClientInfo
    ): Call<MessageRequest>

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