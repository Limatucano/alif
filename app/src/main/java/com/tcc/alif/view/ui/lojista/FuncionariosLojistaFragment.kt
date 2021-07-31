package com.tcc.alif.view.ui.lojista

import android.content.Context
import android.content.Intent
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
import com.tcc.alif.databinding.FragmentFilasLojistaBinding
import com.tcc.alif.databinding.FragmentFuncionariosBinding
import com.tcc.alif.model.LojistaInfo
import com.tcc.alif.model.MeusFuncionarios
import com.tcc.alif.model.MinhasFilas
import com.tcc.alif.model.RestApiService
import com.tcc.alif.model.domain.MeusFuncionariosData
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.view.adapter.MeusFuncionariosAdapter
import com.tcc.alif.view.adapter.MinhasFilasAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FuncionariosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FuncionariosLojistaFragment : Fragment(), MeusFuncionariosAdapter.OnClickItemListener  {
    private val viewBinding : FragmentFuncionariosBinding by viewBinding()
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
        return inflater.inflate(R.layout.fragment_funcionarios, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMyFuncionarios()
        viewBinding.btnCadastrarFuncionario.setOnClickListener {
            val intent = Intent(context, FormFuncionarioLojistaActivity::class.java)
            startActivity(intent)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FuncionariosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FuncionariosLojistaFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
    override fun onResume() {
        getMyFuncionarios()
        super.onResume()
    }

    private fun getMyFuncionarios(){
        val preferences = activity?.getSharedPreferences("LojistaData", Context.MODE_PRIVATE) ?: return
        val service = RestApiService()
        val id_lojista = preferences.getInt("id_lojista", 0)
        service.getMyFuncionarios(id_lojista.toString()) { status: Int?, response: MeusFuncionarios? ->

            if(status != 200){
                Toast.makeText(context, "Erro ao buscar seus funcionários :(", Toast.LENGTH_LONG).show()
            }else{
                viewBinding.progressLoading.visibility = View.GONE
                response?.response?.let { funcionarios ->
                    val funcionario: List<MeusFuncionariosData> = funcionarios.map{ funcionario ->
                        MeusFuncionariosData(funcionario.cod_funcionario,funcionario.nome_funcionario,funcionario.cargo, funcionario.id_lojista, funcionario.cpf)
                    }
                    val layoutManager = LinearLayoutManager(context)
                    viewBinding.rvMyFuncionariosLojista.post{
                        viewBinding.rvMyFuncionariosLojista.layoutManager = layoutManager
                        viewBinding.rvMyFuncionariosLojista.adapter = MeusFuncionariosAdapter(funcionario, this)
                    }
                }
            }
        }
    }

    override fun onItemClick(items: MeusFuncionariosData, position: Int) {
        val intent = Intent(context, FormFuncionarioLojistaActivity::class.java)
        items.apply {
            val funcionario: HashMap<String, Any?> = hashMapOf(
                    "nome_funcionario" to this.nome_funcionario,
                    "cod_funcionario" to this.cod_funcionario,
                    "cargo" to this.cargo,
                    "id_lojista" to this.id_lojista,
                    "cpf" to this.cpf
            )
            val b = Bundle()
            b.putSerializable("funcionario", funcionario)
            intent.putExtras(b)
            startActivity(intent)
        }
    }
}