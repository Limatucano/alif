package com.tcc.alif.view.ui.cliente

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentPesquisarClienteBinding
import com.tcc.alif.model.*
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.model.restApiService.usuarioService
import com.tcc.alif.view.adapter.PesquisaFilasAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PesquisarClienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PesquisarClienteFragment : Fragment(R.layout.fragment_pesquisar_cliente) , PesquisaFilasAdapter.OnClickItemListener {
    private val viewBinding : FragmentPesquisarClienteBinding by viewBinding()

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesquisar_cliente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.filasFiltro.isChecked = true
        viewBinding.empresasFiltro.isChecked = false


        val apiService = usuarioService()

        viewBinding.buttonFiltro.setOnClickListener {

            if(viewBinding.filasFiltro.isChecked){
                val arr = MinhasFilasResponse(
                        nome_da_fila = viewBinding.campoPesquisar.text.toString(),
                )

                apiService.getFilasByName(arr){ status: Int?, minhasFilas: MinhasFilas? ->
                    if (status != 200) {
                        Toast.makeText(context, R.string.erro_pegar_fila, Toast.LENGTH_LONG).show()
                    } else {
                        minhasFilas?.response?.let {
                            val fila : List<MinhasFilasData> = it.map{ fila ->
                                MinhasFilasData(
                                    nome_da_fila = fila.nome_da_fila,
                                    id_fila = fila.id_fila,
                                    quantidade_vagas = fila.quantidade_vagas,
                                    horario_abertura = fila.horario_abertura,
                                    horario_fechamento = fila.horario_fechamento,
                                    tempo_medio = fila.tempo_medio,
                                    id_lojista = fila.id_lojista,
                                    nome_fantasia = fila.nome_fantasia,
                                    quantidade_por_fila = fila.quantidade_por_fila)
                            }

                            val layoutManager = LinearLayoutManager(context)
                            viewBinding.rvResultado.post{
                                viewBinding.rvResultado.layoutManager = layoutManager
                                viewBinding.rvResultado.adapter = PesquisaFilasAdapter(fila, this)
                            }
                        }

                    }
                }
            }
            if(viewBinding.empresasFiltro.isChecked){
                //TODO: filtrar lojistas aqui
            }
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PesquisarClienteFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PesquisarClienteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(items: MinhasFilasData, position: Int) {
        val intent = Intent(context, DetalheFilaClienteActivity::class.java)

        items.apply {
            val fila: HashMap<String, Any?> = hashMapOf(
                "id_lojista" to this.id_lojista,
                "id_fila" to this.id_fila,
                "quantidade_vagas" to this.quantidade_vagas,
                "nome_da_fila" to this.nome_da_fila,
                "horario_abertura" to this.horario_abertura,
                "horario_fechamento" to this.horario_fechamento,
                "tempo_medio" to this.tempo_medio,
                "nome_fantasia" to this.nome_fantasia,
                "quantidade_por_fila" to this.quantidade_por_fila
            )
            val b = Bundle()
            b.putSerializable("fila", fila)
            intent.putExtras(b)
            context?.startActivity(intent)
        }
    }
}