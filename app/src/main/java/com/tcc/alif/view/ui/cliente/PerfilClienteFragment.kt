package com.tcc.alif.view.ui.cliente

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.FragmentPerfilClienteBinding
import com.tcc.alif.databinding.FragmentPerfilLojistaBinding
import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.MessageRequest
import com.tcc.alif.model.restApiService.usuarioService
import javax.net.ssl.HttpsURLConnection

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondClienteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilClienteFragment : Fragment() {
    private val viewBinding : FragmentPerfilClienteBinding by viewBinding()
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
        return inflater.inflate(R.layout.fragment_perfil_cliente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       val apiService = usuarioService()
       val clientData = activity?.getSharedPreferences("ClientData", Context.MODE_PRIVATE) ?: return
       val clientSet = mapOf(
           "id_cliente" to clientData.getInt("id_cliente", 0),
           "nome" to clientData.getString("nome", ""),
           "cpf" to clientData.getString("cpf", ""),
           "nascimento" to clientData.getString("nascimento", ""),
           "numero_celular" to clientData.getString("numero_celular", ""),
           "email" to clientData.getString("email", "")
       )
       viewBinding.editNome.setText(clientSet["nome"].toString())
       viewBinding.editCPF.setText(clientSet["cpf"].toString())
       viewBinding.editCelular.setText(clientSet["numero_celular"].toString())
       viewBinding.editEmail.setText(clientSet["email"].toString())


       viewBinding.salvarPerfil.setOnClickListener {
           val userData = ClientInfo(
               id_cliente = clientSet["id_cliente"].toString().toInt(),
               nome = viewBinding.editNome.text.toString(),
               cpf = viewBinding.editCPF.text.toString(),
               numero_celular = viewBinding.editCelular.text.toString(),
               email = viewBinding.editEmail.text.toString()
           )
           apiService.atualizarPerfilCliente(userData){status: Int?, response: MessageRequest? ->
               Log.d("TESTE",userData.toString())
                when(status){
                    HttpsURLConnection.HTTP_OK -> {
                        Toast.makeText(
                            context,
                            "Atualizado com sucesso",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "Erro ao atualizar :(",
                            Toast.LENGTH_LONG
                        ).show()
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
         * @return A new instance of fragment SecondClienteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerfilClienteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}