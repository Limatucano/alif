package com.tcc.alif

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.databinding.ActivityDetalheFilaClienteBinding
import com.tcc.alif.databinding.ActivityDetalheFilaHomeBinding
import com.tcc.alif.model.FilaInfo
import com.tcc.alif.model.MinhasFilas
import com.tcc.alif.model.restApiService.usuarioService

class DetalheFilaClienteActivity : AppCompatActivity() {
    private val viewBinding : ActivityDetalheFilaClienteBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_fila_cliente)
        val fila = intent?.getSerializableExtra("fila") as HashMap<*, *>?


        if(!fila.isNullOrEmpty()){
            var tempoMedio = fila["tempo_medio"].toString().toInt() * fila["quantidade_por_fila"].toString().toInt()
            val filaInfo = FilaInfo(
                id_fila = fila["id_fila"].toString().toInt(),
            )
            val apiService = usuarioService()
            apiService.getFilaById(filaInfo){ status: Int?, minhasFilas: MinhasFilas? ->
                if (status == 200) {
                    minhasFilas?.response?.get(0)?.let{
                        if(!it.tempo_medio.isNullOrEmpty() && !it.quantidade_por_fila.isNullOrEmpty()){
                           tempoMedio = it.tempo_medio.toString().toInt() * it.quantidade_por_fila.toString().toInt()
                           viewBinding.tempoMedio.text = getString(R.string.tempo_para_ser_atendido,tempoMedio.toString())
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

        viewBinding.inscrever.setOnClickListener {
            val clientData = this.getSharedPreferences("ClientData", Context.MODE_PRIVATE)
            val id_fila = fila?.get("id_fila")
            val id_cliente = clientData.getInt("id_cliente", 0)

            Log.d("TESTE FILA", id_fila.toString())
            Log.d("TESTE CLIENTE", id_cliente.toString())
        }
    }
}
