package com.tcc.alif.view.ui.lojista

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.FormFilaLojistaActivity
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentFilasLojistaBinding
import com.tcc.alif.databinding.FragmentPerfilLojistaBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PerfilLojistaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilLojistaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewBinding : FragmentPerfilLojistaBinding by viewBinding()
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
        return inflater.inflate(R.layout.fragment_perfil_lojista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lojistaData = activity?.getSharedPreferences("LojistaData", Context.MODE_PRIVATE) ?: return
        val lojistaSet = mapOf(
                "email" to lojistaData.getString("email", ""),
                "id_lojista" to lojistaData.getInt("id_lojista", 0),
                "ocupacao" to lojistaData.getString("ocupacao", ""),
                "nome_fantasia" to lojistaData.getString("nome_fantasia", ""),
                "doc" to lojistaData.getString("doc", ""),
                "nome" to lojistaData.getString("nome", "")
        )

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PerfilLojistaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerfilLojistaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}