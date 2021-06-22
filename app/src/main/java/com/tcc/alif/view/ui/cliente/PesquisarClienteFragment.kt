package com.tcc.alif.view.ui.cliente

import android.os.Bundle
import android.util.Log
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
import com.tcc.alif.view.adapter.PesquisaFilasAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PesquisarClienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PesquisarClienteFragment : Fragment(R.layout.fragment_pesquisar_cliente) , PesquisaFilasAdapter.OnClickItemListener {
    private val viewBinding : FragmentPesquisarClienteBinding by viewBinding()
    // TODO: Rename and change types of parameters
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


        val apiService = RestApiService()

        viewBinding.buttonFiltro.setOnClickListener {
            val arr = MinhasFilasResponse(
                    nome_da_fila = viewBinding.campoPesquisar.text.toString(),
                    )

            apiService.getFilasByName(arr){ status: Int?, minhasFilas: MinhasFilas? ->
                if (status != 200) {
                    Toast.makeText(context, R.string.erro_pegar_fila, Toast.LENGTH_LONG).show()
                    //Snackbar.make(viewBinding., R.string.erro_pegar_fila, Snackbar.LENGTH_LONG ).show()
                } else {
                    minhasFilas?.response?.let {
                        val fila : List<MinhasFilasData> = it.map{ fila ->
                            MinhasFilasData(fila.nome_da_fila, fila.id_fila, fila.quantidade_vagas, fila.horario_abertura, fila.horario_fechamento, fila.intervalo, fila.id_lojista)
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
        viewBinding.filasFiltro.setOnClickListener {
            //filterFilas(arr)
        }
        viewBinding.empresasFiltro.setOnClickListener {
            //filterFilas(arr)
        }

    }

    fun filterFilas(arr: MinhasFilasResponse){

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
        // TODO: Rename and change types and number of parameters
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
        TODO("Not yet implemented")
    }
}