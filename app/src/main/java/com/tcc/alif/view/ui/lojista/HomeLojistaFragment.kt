package com.tcc.alif.view.ui.lojista

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentHomeLojistaBinding
import com.tcc.alif.model.*
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.view.adapter.MinhasFilasHomeAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeLojistaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeLojistaFragment : Fragment(), MinhasFilasHomeAdapter.OnClickItemListener {
    // TODO: Rename and change types of parameters
    private val viewBinding : FragmentHomeLojistaBinding by viewBinding()
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
        return inflater.inflate(R.layout.fragment_home_lojista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val preferences = activity?.getSharedPreferences("LojistaData", Context.MODE_PRIVATE) ?: return
        val service = RestApiService()
        val id_lojista = preferences.getInt("id_lojista", 0)
        getData(service,id_lojista)

    }

    fun getData(service : RestApiService, id_lojista : Int){
        val data = LojistaInfo(id_lojista = id_lojista)
        service.getMyFilasLojista(data) { status: Int?, response: MinhasFilas? ->
            if(status != 200){
                activity?.runOnUiThread {
                    Toast.makeText(context, R.string.erro_pegar_fila, Toast.LENGTH_LONG).show()
                }
            }else{
                viewBinding.progressLoading.visibility = View.GONE
                service.getMeusPrimeirosClientes(data){s: Int?, r: MeusPrimeirosClientes? ->
                    r?.response.let { meusClientes ->
                        response?.response?.let { filas ->
                            val fila: List<MinhasFilasData> = filas.map{ fila ->
                                MinhasFilasData(fila.nome_da_fila,fila.id_fila,fila.quantidade_vagas,fila.horario_abertura, fila.horario_fechamento,fila.tempo_medio, fila.id_lojista, meusClientes?.get(fila.id_fila.toString()))
                            }
                            val layoutManager = LinearLayoutManager(context)
                            activity?.runOnUiThread{
                                viewBinding.rvFilasHome.post{
                                    viewBinding.rvFilasHome.layoutManager = layoutManager
                                    viewBinding.rvFilasHome.adapter = MinhasFilasHomeAdapter(fila, this, context)
                                }
                            }
                        }
                    }
                }
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
         * @return A new instance of fragment HomeLojistaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeLojistaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(items: MinhasFilasData, position: Int)  {
    }
}