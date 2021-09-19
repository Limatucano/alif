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
    * Pega os primeiros clientes da fila para compor o resumo
    *
    * @param  lojistaInfo objeto com todos dados do lojista
    *         onResult Função de callback
    *
    * */
    fun getMeusPrimeirosClientes(lojistaInfo: LojistaInfo, onResult: (Int?, MeusPrimeirosClientes?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.meusprimeirosclientes(lojistaInfo).enqueue(
                object : Callback<MeusPrimeirosClientes>{
                    override fun onResponse(call: Call<MeusPrimeirosClientes>, response: Response<MeusPrimeirosClientes>) {
                        onResult(response.code(), response.body())
                    }

                    override fun onFailure(call: Call<MeusPrimeirosClientes>, t: Throwable) {
                        onResult(500, null)
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
    * Atualiza fila com base no seu id
    *
    * @param  filaData todos campos são enviados
    *         onResult Função de callback
    *
    * */
    fun updateFila(filaData:FilaInfo, onResult: (Int?, MessageRequest?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.updateFila(filaData).enqueue(
                object : Callback<MessageRequest>{
                    override fun onFailure(call: Call<MessageRequest>, t: Throwable) {
                        onResult(500,null)
                    }

                    override fun onResponse(call: Call<MessageRequest>, response: Response<MessageRequest>) {
                        onResult(response.code(), response.body())
                    }
                }
        )
    }

    /*
    * Deletar fila com base no seu id
    *
    * @param  id_fila id da fila
    *         onResult Função de callback
    *
    * */
    fun deleteFila(id_fila: String, onResult: (Int?, MessageRequest?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.deleteFila(id_fila).enqueue(
            object : Callback<MessageRequest>{
                override fun onFailure(call: Call<MessageRequest>, t: Throwable) {
                    onResult(500, null)
                }

                override fun onResponse(call: Call<MessageRequest>, response: Response<MessageRequest>) {
                    onResult(response.code(), response.body())
                }
            }
        )
    }
    /*
    * Pega todas filas correspondente ao lojista
    *
    * @param  userData Informações da fila
    *         onResult Função de callback
    *
    * */
    fun getMyFilasLojista(userData: LojistaInfo, onResult: (Int?, MinhasFilas?)-> Unit){
        val retrofit = ServiceBuilder.buildService(AlifService::class.java)
        retrofit.getMyFilasLojista(userData).enqueue(
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