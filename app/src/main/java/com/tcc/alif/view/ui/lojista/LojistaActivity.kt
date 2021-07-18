package com.tcc.alif.view.ui.lojista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityClienteBinding
import com.tcc.alif.model.ClientInfo
import com.tcc.alif.model.LojistaData
import com.tcc.alif.model.LojistaInfo
import com.tcc.alif.model.RestApiService
import com.tcc.alif.view.ui.cliente.ClienteActivity

class LojistaActivity : AppCompatActivity() {
    private val viewBinding : ActivityClienteBinding by viewBinding()
    var lojistaData: LojistaData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lojista)

        var email = this.intent.getSerializableExtra("email")
        val data = LojistaInfo(
            email = email.toString()
        )

        val apiService = RestApiService()
        apiService.getLojistaData(data) {status: Int?, response: LojistaInfo? ->
            if (status == 200) {
                lojistaData = LojistaData(
                    response?.id_lojista.toString().toInt(),
                    response?.ocupacao.toString(),
                    response?.nome_fantasia.toString(),
                    response?.email.toString(),
                    response?.doc.toString(),
                    response?.nome.toString()
                    )
                viewBinding.nomeCliente.text = lojistaData?.nome_fantasia.toString()
                Log.d("TESTE", lojistaData.toString())
            }
        }

        val funcionariosLojistaFragment = FuncionariosLojistaFragment()
        val homeLojistaFragment = HomeLojistaFragment()
        val filasLojistaFragment = FilasLojistaFragment()
        val perfilLojistaFragment = PerfilLojistaFragment()
        val configLojistaFragment = ConfigLojistaFragment()

        setCurrentFragment(homeLojistaFragment)
        viewBinding.bottomNavigationView.menu.getItem(2).isChecked = true
        viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_func->setCurrentFragment(homeLojistaFragment)
                R.id.funcionarios_func->setCurrentFragment(funcionariosLojistaFragment)
                R.id.filas_func->setCurrentFragment(filasLojistaFragment)
                R.id.config_func->setCurrentFragment(configLojistaFragment)
                R.id.perfil_func->setCurrentFragment(perfilLojistaFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragment)
        commit()
    }
}