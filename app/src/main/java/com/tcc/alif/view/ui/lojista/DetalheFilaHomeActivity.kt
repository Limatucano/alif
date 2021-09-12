package com.tcc.alif.view.ui.lojista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityDetalheFilaHomeBinding

class DetalheFilaHomeActivity : AppCompatActivity() {
    private val viewBinding : ActivityDetalheFilaHomeBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_fila_home)
        val fila = intent?.getSerializableExtra("fila") as HashMap<*, *>?

        if(!fila.isNullOrEmpty()){
            viewBinding.nomeFila.text = fila["nome_da_fila"].toString()
            viewBinding.quantidadeFila.text = getString(R.string.quantidade_placeholder,fila["quantidade_vagas"])
            viewBinding.minutosMedia.text = getString(R.string.minutosMedia_placeholder,fila["tempo_medio"].toString())
            viewBinding.horarioAbertura.text = fila["horario_abertura"].toString()
            viewBinding.horarioFechamento.text = fila["horario_fechamento"].toString()
            viewBinding.numeroFila.text = fila["id_fila"].toString().padStart(5,'0')
        }
        viewBinding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}