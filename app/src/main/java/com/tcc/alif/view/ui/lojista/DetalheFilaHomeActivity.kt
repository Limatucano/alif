package com.tcc.alif.view.ui.lojista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityDetalheFilaHomeBinding
import com.tcc.alif.model.MeusClientesFila
import com.tcc.alif.model.RestApiService
import com.tcc.alif.model.domain.MeusClientesFilaData
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.view.adapter.MeusClientesFilaAdapter
import com.tcc.alif.view.adapter.MinhasFilasHomeAdapter

class DetalheFilaHomeActivity : AppCompatActivity() , MeusClientesFilaAdapter.OnClickItemListener{
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
            getData(fila["id_fila"].toString())
        }
        viewBinding.btnVoltar.setOnClickListener {
            finish()
        }
    }
    fun getData(id_fila:String){
        val service = RestApiService()
        service.getMeusClientesFila(id_fila){ status:Int?, response: MeusClientesFila? ->
            if(status != 200){
                Toast.makeText(this,"Erro ao obter seus clientes :c ", Toast.LENGTH_LONG).show()
                viewBinding.progressLoading.visibility = View.GONE
            }else{
                response?.response?.let{ clientes ->
                    viewBinding.totalPessoasFila.text = getString(R.string.total_pessoas_fila,clientes.size.toString())
                    val cliente: List<MeusClientesFilaData> = clientes.map{ cliente ->
                        MeusClientesFilaData(cliente.nome_completo,cliente.numero_celular,cliente.email,cliente.nascimento)
                    }
                    val layoutManager = LinearLayoutManager(this)
                    viewBinding.rvMeusClientesFila.post{
                        viewBinding.rvMeusClientesFila.layoutManager = layoutManager
                        viewBinding.rvMeusClientesFila.adapter = MeusClientesFilaAdapter(cliente,this)
                    }
                }
                viewBinding.progressLoading.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(items: MeusClientesFilaData, position: Int) {
        TODO("Not yet implemented")
    }
}