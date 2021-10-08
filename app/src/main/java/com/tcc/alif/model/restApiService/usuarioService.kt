package com.tcc.alif.model.restApiService

import android.util.Log
import com.tcc.alif.model.*
import com.tcc.alif.model.api.AlifService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class usuarioService {


    /*
* Registra um novo cliente
*
* @param  userData Informações do cliente
*         onResult Função de callback
*
* */
    fun registerClient(userData: ClientInfo, onResult : (Int?, MessageRequest?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.registerClient(userData).enqueue(
            object : Callback<MessageRequest> {
                override fun onFailure(call: Call<MessageRequest>, t: Throwable) {
                    onResult(500,null)
                }
                override fun onResponse(call: Call<MessageRequest>, response: Response<MessageRequest>) {
                    val addedClient = response.body()
                    val status = response.code()
                    onResult(status,addedClient)
                }
            }
        )
    }
    /*
    * Método para verificar se email já existe
    *
    * @param  userData Informações do cliente
    *         onResult Função de callback
    *
    * */
    fun verifyEmail(userData: ClientInfo, onResult : (Int?, ClientInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.verifyEmail(userData).enqueue(
            object : Callback<ClientInfo> {
                override fun onFailure(call: Call<ClientInfo>, t: Throwable) {
                    onResult(500,null)
                }
                override fun onResponse(call: Call<ClientInfo>, response: Response<ClientInfo>) {
                    val  addedClient = response.body()
                    val status = response.code()
                    if(response.code() != 200){
                        onResult(status,addedClient)
                    }else{
                        onResult(status,addedClient)
                    }

                }

            }
        )
    }

    /*
    * Realiza o login do cliente
    *
    * @param  userData Informações do cliente
    *         onResult Função de callback
    *
    * */
    fun login(userData: ClientInfo, onResult: (Int?, ClientInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.login(userData).enqueue(
            object : Callback<ClientInfo> {
                override fun onFailure(call: Call<ClientInfo>, t: Throwable) {
                    onResult(500,null)
                }
                override fun onResponse(call: Call<ClientInfo>, response: Response<ClientInfo>) {
                    val  addedClient = response.body()
                    val status = response.code()
                    if(response.code() != 200){
                        onResult(status,addedClient)
                    }else{
                        onResult(status,addedClient)
                    }

                }

            }
        )
    }
    /*
    * Pega todas filas correspondente ao usuário
    *
    * @param  userData Informações da fila
    *         onResult Função de callback
    *
    * */
    fun getMyFilas(userData: MinhasFilasPost, onResult: (Int?, MinhasFilas?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.getMyFilas(userData).enqueue(
            object : Callback<MinhasFilas> {
                override fun onFailure(call: Call<MinhasFilas>, t: Throwable) {
                    onResult(500,null)
                }
                override fun onResponse(call: Call<MinhasFilas>, response: Response<MinhasFilas>) {
                    val  addedClient = response.body()
                    val status = response.code()
                    if(response.code() != 200){
                        onResult(status,addedClient)
                    }else{
                        onResult(status,addedClient)
                    }

                }

            }
        )
    }
    /*
    * Filtra as filas por nome
    *
    * @param  userData Informações da fila
    *         onResult Função de callback
    *
    * */
    fun getFilasByName(userData: MinhasFilasResponse, onResult: (Int?, MinhasFilas?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.getFilasByName(userData).enqueue(
            object : Callback<MinhasFilas> {
                override fun onFailure(call: Call<MinhasFilas>, t: Throwable) {
                    onResult(500,null)
                }
                override fun onResponse(call: Call<MinhasFilas>, response: Response<MinhasFilas>) {
                    val  addedClient = response.body()
                    val status = response.code()
                    if(response.code() != 200){
                        onResult(status,addedClient)
                    }else{
                        onResult(status,addedClient)
                    }

                }

            }
        )
    }

}