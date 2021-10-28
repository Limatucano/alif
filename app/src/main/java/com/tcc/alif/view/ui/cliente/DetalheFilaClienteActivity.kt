package com.tcc.alif.view.ui.cliente

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityDetalheFilaClienteBinding
import com.tcc.alif.model.FilaInfo
import com.tcc.alif.model.MessageRequest
import com.tcc.alif.model.MinhasFilas
import com.tcc.alif.model.inscreverFilaPost
import com.tcc.alif.model.restApiService.usuarioService
import javax.net.ssl.HttpsURLConnection

class DetalheFilaClienteActivity : AppCompatActivity() {
    private val viewBinding : ActivityDetalheFilaClienteBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_fila_cliente)
        val fila = intent?.getSerializableExtra("fila") as HashMap<*, *>?
        val apiService = usuarioService()

        if(!fila.isNullOrEmpty()){
            var tempoMedio = 0
            if(fila["tempo_medio"].toString().isNotEmpty() && fila["quantidade_por_fila"].toString().isNotEmpty()){
                tempoMedio = fila["tempo_medio"].toString().toInt() * fila["quantidade_por_fila"].toString().toInt()
            }
            when(fila["is_minha_fila"]){
                true -> {
                    viewBinding.inscrever.visibility = View.GONE
                    viewBinding.cancelar.visibility = View.VISIBLE
                }
                false -> {
                    viewBinding.inscrever.visibility = View.VISIBLE
                    viewBinding.cancelar.visibility = View.GONE
                }
            }
            val filaInfo = FilaInfo(
                id_fila = fila["id_fila"].toString().toInt(),
            )
            if(fila["is_minha_fila"].toString().toBoolean()){
                Log.d("TESTE", fila["is_minha_fila"].toString() + "certo")
            }else{
                Log.d("TESTE", fila["is_minha_fila"].toString() + "errado")
            }
            apiService.getFilaById(filaInfo){ status: Int?, minhasFilas: MinhasFilas? ->
                if (status == 200) {
                    minhasFilas?.response?.get(0)?.let{
                        if(!it.tempo_medio.isNullOrEmpty() && !it.quantidade_por_fila.isNullOrEmpty()){
                           tempoMedio = it.tempo_medio.toString().toInt() * it.quantidade_por_fila.toString().toInt()
                           viewBinding.tempoMedio.text = getString(R.string.tempo_para_ser_atendido,tempoMedio.toString())
                           viewBinding.qtdPessoasFila.text = getString(R.string.pessoas,it.quantidade_por_fila.toString())
                        }
                    }
                }
            }

            viewBinding.nomeFila.text = fila["nome_da_fila"].toString()
            viewBinding.qtdPessoasFila.text = getString(R.string.pessoas,fila["quantidade_por_fila"])
            viewBinding.tempoMedio.text = getString(R.string.tempo_para_ser_atendido,tempoMedio.toString())
            viewBinding.horaAbertura.text = fila["horario_abertura"].toString()
            viewBinding.horaFechamento.text = fila["horario_fechamento"].toString()
            viewBinding.lojista.text = fila["nome_fantasia"].toString()
            //viewBinding.numeroFila.text = fila["id_fila"].toString().padStart(5,'0')
        }
        viewBinding.btnVoltar.setOnClickListener {
            finish()
        }

        viewBinding.cancelar.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_cancelar_fila,null)
            val builder = AlertDialog.Builder(this).setView(dialogView).show()

            dialogView.findViewById<TextView>(R.id.confirmarSaida).setOnClickListener {
                builder.dismiss()
            }
        }

        viewBinding.inscrever.setOnClickListener {
            val clientData = this.getSharedPreferences("ClientData", Context.MODE_PRIVATE)
            val id_fila = fila?.get("id_fila")
            val id_cliente = clientData.getInt("id_cliente", 0)

            val data = inscreverFilaPost(
                id_fila = id_fila.toString(),
                id_cliente = id_cliente.toString()
            )

            apiService.inscreverClienteFila(data){ status: Int?, message: MessageRequest? ->
                when(status){
                    HttpsURLConnection.HTTP_CREATED -> {
                        finish()
                        Toast.makeText(this, R.string.inserido_com_sucesso_na_fila, Toast.LENGTH_LONG).show()
                    }
                    HttpsURLConnection.HTTP_CONFLICT -> Toast.makeText(this, R.string.ja_cadastrado_na_fila, Toast.LENGTH_LONG).show()
                    else -> Toast.makeText(this, R.string.erro_cadastrar_na_fila, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
