package com.tcc.alif.view.ui.cliente

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.MinhasFilas
import com.tcc.alif.model.MinhasFilasPost
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.model.restApiService.usuarioService
import com.tcc.alif.view.adapter.MinhasFilasAdapter


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstClienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeClienteFragment : Fragment(R.layout.fragment_first_cliente), MinhasFilasAdapter.OnClickItemListener {

    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_cliente, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        val view = getView()
        if(view !== null){
            getMinhasFilas(view)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMinhasFilas(view)
    }
    fun getMinhasFilas(view: View){
        val rvFilas = view.findViewById<RecyclerView>(R.id.rvFilas)
        val apiService = usuarioService()

        var email = activity?.intent?.getSerializableExtra("email")
        val arr = MinhasFilasPost(
            email = email.toString(),
        )
        apiService.getMyFilas(arr){ status: Int?, minhasFilas: MinhasFilas? ->

            if (status != 200) {
                Toast.makeText(context, R.string.erro_pegar_fila, Toast.LENGTH_LONG).show()
            } else {
                minhasFilas?.response?.let {
                    val fila : List<MinhasFilasData> = it.map{ fila ->
                        MinhasFilasData(fila.nome_da_fila, fila.id_fila, fila.quantidade_vagas, fila.horario_abertura, fila.horario_fechamento, fila.tempo_medio, fila.id_lojista)
                    }

                    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvFilas.post{
                        rvFilas.layoutManager = layoutManager
                        rvFilas.adapter = MinhasFilasAdapter(fila, this)
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
         * @return A new instance of fragment FirstClienteFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeClienteFragment().apply {
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
                "quantidade_por_fila" to this.quantidade_por_fila,
                "is_minha_fila" to true,
            )

            val b = Bundle()
            b.putSerializable("fila", fila)
            intent.putExtras(b)
            context?.startActivity(intent)
        }
    }
}