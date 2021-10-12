package com.tcc.alif.view.ui.cliente

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityClienteBinding
import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.MinhasFilasPost
import com.tcc.alif.model.domain.MinhasFilasData
import com.tcc.alif.model.restApiService.usuarioService
import com.tcc.alif.view.adapter.MinhasFilasAdapter
import com.tcc.alif.view.ui.cliente.HomeClienteFragment
import com.tcc.alif.view.ui.cliente.PerfilClienteFragment

class ClienteActivity : AppCompatActivity() , MinhasFilasAdapter.OnClickItemListener {
    private val viewBinding : ActivityClienteBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)

        val apiService = usuarioService()

        var email = this.intent.getSerializableExtra("email")

        apiService.getClientData(email.toString()){status: Int?, response: ClientInfo? ->

            if(status == 200){
                val clientData = this.getSharedPreferences("ClientData", Context.MODE_PRIVATE)
                with(clientData.edit()){
                    putInt("id_cliente", response?.id_cliente.toString().toInt())
                    putString("nome", response?.nome.toString())
                    putString("cpf", response?.cpf.toString())
                    putString("nascimento", response?.nascimento.toString())
                    putString("numero_celular", response?.numero_celular.toString())
                    putString("email", response?.email.toString())
                    apply()
                }
                val nome = clientData.getString("nome", "")
                viewBinding.nomeCliente.text = nome
            }

        }

        val HomeClienteFragment = HomeClienteFragment()
        val PerfilClienteFragment = PerfilClienteFragment()
        val PesquisarClienteFragment = PesquisarClienteFragment()
        val FavoritosClienteFragment = FavoritosClienteFragment()
        val ConfigClienteFragment = ConfigClienteFragment()
        setCurrentFragment(HomeClienteFragment)
        viewBinding.bottomNavigationView.menu.getItem(2).isChecked = true
        viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(HomeClienteFragment)
                R.id.perfil->setCurrentFragment(PerfilClienteFragment)
                R.id.procurar->setCurrentFragment(PesquisarClienteFragment)
                R.id.favoritos->setCurrentFragment(FavoritosClienteFragment)
                R.id.config->setCurrentFragment(ConfigClienteFragment)
            }
            true
        }
    }

    override fun onItemClick(items: MinhasFilasData, position: Int) {


        Toast.makeText(this, items.nome_da_fila, Toast.LENGTH_LONG).show()
    }
    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragment)
        commit()
    }
}