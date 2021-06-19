package com.tcc.alif

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.tcc.alif.databinding.ActivityClienteBinding
import com.tcc.alif.model.MinhasFilas
import com.tcc.alif.model.MinhasFilasPost
import com.tcc.alif.model.RestApiService
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.view.adapter.MinhasFilasAdapter
import com.tcc.alif.view.ui.ClienteActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstClienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstClienteFragment : Fragment(R.layout.fragment_first_cliente), MinhasFilasAdapter.OnClickItemListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewBinding : FirstClienteFragment by viewBinding()
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rvFilas = view.findViewById<RecyclerView>(R.id.rvFilas)

        val apiService = RestApiService()

        var email = activity?.intent?.getSerializableExtra("email")
        val arr = MinhasFilasPost(
            email = email.toString(),
        )
        apiService.getMyFilas(arr){ status: Int?, minhasFilas: MinhasFilas? ->

            if (status != 200) {
                Toast.makeText(context, R.string.erro_pegar_fila, Toast.LENGTH_LONG).show()
                //Snackbar.make(viewBinding., R.string.erro_pegar_fila, Snackbar.LENGTH_LONG ).show()
            } else {
                minhasFilas?.response?.let {
                    val fila : List<MinhasFilasData> = it.map{ fila ->
                        MinhasFilasData(fila.nome_da_fila, fila.id_fila, fila.quantidade_vagas, fila.horario_abertura, fila.horario_fechamento, fila.intervalo, fila.id_lojista)
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FirstClienteFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onItemClick(items: MinhasFilasData, position: Int) {
        Toast.makeText(context, items.nome_da_fila, Toast.LENGTH_LONG).show()
    }
}