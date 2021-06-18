package com.tcc.alif.model

import android.util.Log
import com.tcc.alif.model.api.AlifService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
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
                        Log.d("TESTANDOO",addedClient.toString())
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