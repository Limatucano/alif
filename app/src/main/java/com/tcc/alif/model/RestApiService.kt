package com.tcc.alif.model

import android.util.Log
import com.tcc.alif.model.api.AlifService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    /*
    * Resgata informações do lojista
    *
    * @param  data Informações do lojista
    *         onResult Função de callback
    *
    * */
    fun getLojistaData(data: LojistaInfo, onResult: (Int?, LojistaInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.getLojistaData(data).enqueue(
            object : Callback<LojistaInfo>{
                override fun onResponse(call: Call<LojistaInfo>, response: Response<LojistaInfo>) {
                    val data = response.body()
                    val status = response.code()
                    Log.d("STATUS", response.code().toString())
                    onResult(status,data)
                }

                override fun onFailure(call: Call<LojistaInfo>, t: Throwable) {
                    onResult(500,null)
                }

            }
        )
    }

    /*
    * Registra uma nova fila
    *
    * @param  filaData Informações da fila
    *         onResult Função de callback
    *
    * */
    fun registerFila(filaData: FilaInfo, onResult: (Int?, FilaInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.registerFila(filaData).enqueue(
                object : Callback<FilaInfo> {
                    override fun onFailure(call: Call<FilaInfo>, t: Throwable) {
                        onResult(500,null)
                    }

                    override fun onResponse(call: Call<FilaInfo>, response: Response<FilaInfo>) {
                        val addedFila = response.body()
                        val status = response.code()
                        Log.d("STATUS", response.code().toString())
                        if(response.code() != 200){
                            onResult(status,addedFila)
                        }else{
                            onResult(status,addedFila)
                        }
                    }
                }
        )
    }

    /*
    * Registra um novo lojista
    *
    * @param  userData Informações do lojista
    *         onResult Função de callback
    *
    * */
    fun registerLojista(userData: LojistaInfo, onResult: (Int?, LojistaInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.registerLojista(userData).enqueue(
            object : Callback<LojistaInfo> {
                override fun onFailure(call: Call<LojistaInfo>, t: Throwable) {
                    onResult(500,null)
                }
                override fun onResponse(call: Call<LojistaInfo>, response: Response<LojistaInfo>) {
                    val  addedClient = response.body()
                    val status = response.code()
                    Log.d("STATUS", response.code().toString())
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
    * Registra um novo cliente
    *
    * @param  userData Informações do cliente
    *         onResult Função de callback
    *
    * */
    fun registerClient(userData: ClientInfo, onResult : (Int?,ClientInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.registerClient(userData).enqueue(
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
    * Método para verificar se email já existe
    *
    * @param  userData Informações do cliente
    *         onResult Função de callback
    *
    * */
    fun verifyEmail(userData: ClientInfo, onResult : (Int?,ClientInfo?) -> Unit){
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
    * Realiza o login do lojista
    *
    * @param  userData Informações do lojista
    *         onResult Função de callback
    *
    * */
    fun loginLojista(userData: LojistaInfo, onResult: (Int?, LojistaInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.loginLojista(userData).enqueue(
            object : Callback<LojistaInfo> {
                override fun onFailure(call: Call<LojistaInfo>, t: Throwable) {
                    onResult(500,null)
                }
                override fun onResponse(call: Call<LojistaInfo>, response: Response<LojistaInfo>) {
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