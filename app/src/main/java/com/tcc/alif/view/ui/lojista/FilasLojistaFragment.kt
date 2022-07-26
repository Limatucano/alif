package com.tcc.alif.view.ui.lojista

import android.content.Context
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
import com.tcc.alif.databinding.FragmentFilasLojistaBinding
import com.tcc.alif.data.LojistaInfo
import com.tcc.alif.data.MinhasFilas
import com.tcc.alif.data.domain.MinhasFilasData
import com.tcc.alif.view.adapter.MinhasFilasAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilasLojistaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilasLojistaFragment : Fragment(R.layout.fragment_filas_lojista), MinhasFilasAdapter.OnClickItemListener {
    private val viewBinding : FragmentFilasLojistaBinding by viewBinding()
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
        return inflater.inflate(R.layout.fragment_filas_lojista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMyFilas()
        viewBinding.btnCriarFila.setOnClickListener {
            val intent = Intent(context, FormFilaLojistaActivity::class.java)
            startActivity(intent)

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FilasLojistaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        getMyFilas()
        super.onResume()
    }
    override fun onItemClick(items: MinhasFilasData, position: Int) {
    val intent = Intent(context, FormFilaLojistaActivity::class.java)
        items.apply {
            val fila : HashMap<String, Any?> = hashMapOf(
                "id_lojista" to this.id_lojista,
                "id_fila" to this.id_fila,
                "quantidade_vagas" to this.quantidade_vagas,
                "nome_da_fila" to this.nome_da_fila,
                "horario_abertura" to this.horario_abertura,
                "horario_fechamento" to this.horario_fechamento,
                "tempo_medio" to this.tempo_medio
            )
            val b = Bundle()
            b.putSerializable("fila", fila)
            intent.putExtras(b)
            startActivity(intent)
        }
    }
    private fun getMyFilas(){
        val preferences = activity?.getSharedPreferences("LojistaData",Context.MODE_PRIVATE) ?: return
        val email = preferences.getString("email","")
//        val service = lojistaService()
//        val id_lojista = preferences.getInt("id_lojista", 0)
//        val data = LojistaInfo(id_lojista = id_lojista)
//        service.getMyFilasLojista(data) { status: Int?, response: MinhasFilas? ->
//
//            if(status != 200){
//                Toast.makeText(context, R.string.erro_pegar_fila, Toast.LENGTH_LONG).show()
//            }else{
//                viewBinding.progressLoading.visibility = View.GONE
//                response?.response?.let { filas ->
//                    val fila: List<MinhasFilasData> = filas.map{ fila ->
//                        MinhasFilasData(fila.nome_da_fila,fila.id_fila,fila.quantidade_vagas,fila.horario_abertura, fila.horario_fechamento,fila.tempo_medio, fila.id_lojista)
//                    }
//                    val layoutManager = LinearLayoutManager(context)
//                    viewBinding.rvMyFilasLojista.post{
//                        viewBinding.rvMyFilasLojista.layoutManager = layoutManager
//                        viewBinding.rvMyFilasLojista.adapter = MinhasFilasAdapter(fila, this, true)
//                    }
//                }
//            }
//        }
    }
}