package com.tcc.alif.model

import android.util.Log
import com.tcc.alif.model.api.AlifService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    /*
    * Responsavel por atualizar perfil do lojista
    *
    * @param  lojistaInfo objeto com todos dados do lojista
    *         onResult Função de callback
    *
    * */

    fun updateProfileLojista(lojistaInfo: LojistaInfo, onResult: (Int?, MessageRequest?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.updateProfileLojista(lojistaInfo).enqueue(
                object  : Callback<MessageRequest>{
                    override fun onResponse(call: Call<MessageRequest>, response: Response<MessageRequest>) {
                        Log.d("TESTE",response.message().toString())
                        onResult(response.code(), response.body())
                    }

                    override fun onFailure(call: Call<MessageRequest>, t: Throwable) {
                        onResult(500,null)
                    }
                }
        )
    }

    /*
    * Pega todos clientes da fila
    *
    * @param  id_fila string com o id da fila
    *         onResult Função de callback
    *
    * */
    fun getMeusClientesFila(id_fila: String, onResult: (Int?, MeusClientesFila?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.getMeusClientesFila(id_fila).enqueue(
                object : Callback<MeusClientesFila>{
                    override fun onResponse(call: Call<MeusClientesFila>, response: Response<MeusClientesFila>) {
                        onResult(response.code(), response.body())
                    }

                    override fun onFailure(call: Call<MeusClientesFila>, t: Throwable) {
                        onResult(500,null)
                    }

                }
        )
    }
    /*
    * atualiza funcionario
    *
    * @param  funcionario todos dados do funcionario
    *         onResult Função de callback
    *
    * */
    fun updateFuncionario(funcionario:FuncionarioInfo, onResult: (Int?, MessageRequest?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.updateFuncionario(funcionario).enqueue(
                object : Callback<MessageRequest>{
                    override fun onResponse(call: Call<MessageRequest>, response: Response<MessageRequest>) {
                        onResult(response.code(), response.body())
                    }

                    override fun onFailure(call: Call<MessageRequest>, t: Throwable) {
                        onResult(500,null)
                    }

                }
        )
    }

    /*
    * deleta funcionario
    *
    * @param  cod_funcionario código do funcionario, semelhante ao id
    *         onResult Função de callback
    *
    * */
    fun deleteFuncionario(cod_funcionario: String, onResult: (Int?, MessageRequest?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.deleteFuncionario(cod_funcionario).enqueue(
                object : Callback<MessageRequest>{
                    override fun onResponse(call: Call<MessageRequest>, response: Response<MessageRequest>) {
                        onResult(response.code(), response.body())
                    }

                    override fun onFailure(call: Call<MessageRequest>, t: Throwable) {
                        onResult(500, null)
                    }
                }
        )
    }


    /*
    * Pega funcionarios referente ao lojista
    *
    * @param  id_lojista id do lojista
    *         onResult Função de callback
    *
    * */
    fun getMyFuncionarios(id_lojista: String, onResult: (Int?, MeusFuncionarios?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.getMyFuncionarios(id_lojista).enqueue(
            object : Callback<MeusFuncionarios>{
                override fun onFailure(call: Call<MeusFuncionarios>, t: Throwable) {
                    onResult(500, null)
                }

                override fun onResponse(call: Call<MeusFuncionarios>, response: Response<MeusFuncionarios>) {
                    onResult(response.code(), response.body())
                }
            }
        )
    }

    /*
    * Insere um novo funcionario
    *
    * @param  funcionario todos dados do funcionario
    *         onResult Função de callback
    *
    * */
    fun insertNewFuncionario(funcionario:FuncionarioInfo, onResult: (Int?, MessageRequest?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.insertNewFuncionario(funcionario).enqueue(
            object : Callback<MessageRequest>{
                override fun onResponse(call: Call<MessageRequest>, response: Response<MessageRequest>) {
                    onResult(response.code(), response.body())
                }

                override fun onFailure(call: Call<MessageRequest>, t: Throwable) {
                    onResult(500,null)
                }

            }
        )
    }

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
                    onResult(status,data)
                }

                override fun onFailure(call: Call<LojistaInfo>, t: Throwable) {
                    onResult(500,null)
                }

            }
        )
    }


}