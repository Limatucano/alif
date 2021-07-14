package com.tcc.alif.view.ui.lojista

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.FormFilaLojistaActivity
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentFilasLojistaBinding
import com.tcc.alif.databinding.FragmentPesquisarClienteBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilasLojistaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilasLojistaFragment : Fragment() {
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

        viewBinding.btnCriarFila.setOnClickListener {
            var intent = Intent(context, FormFilaLojistaActivity::class.java)
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
}