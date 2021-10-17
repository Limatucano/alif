package com.tcc.alif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.databinding.ActivityDetalheFilaClienteBinding
import com.tcc.alif.databinding.ActivityDetalheFilaHomeBinding

class DetalheFilaClienteActivity : AppCompatActivity() {
    private val viewBinding : ActivityDetalheFilaClienteBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_fila_cliente)
        val fila = intent?.getSerializableExtra("fila") as HashMap<*, *>?

        if(!fila.isNullOrEmpty()){
            viewBinding.nomeFila.text = fila["nome_da_fila"].toString()
            viewBinding.qtdPessoasFila.text = getString(R.string.pessoas,fila["quantidade_vagas"])
            viewBinding.tempoMedio.text = getString(R.string.tempo_para_ser_atendido,fila["tempo_medio"].toString())
            viewBinding.horaAbertura.text = fila["horario_abertura"].toString()
            viewBinding.horaFechamento.text = fila["horario_fechamento"].toString()
            viewBinding.lojista.text = fila["nome_fantasia"].toString()
            //viewBinding.numeroFila.text = fila["id_fila"].toString().padStart(5,'0')
        }
        viewBinding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}