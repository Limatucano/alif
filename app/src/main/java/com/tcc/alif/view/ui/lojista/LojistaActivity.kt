package com.tcc.alif.view.ui.lojista

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityClienteBinding
import com.tcc.alif.model.LojistaInfo
import com.tcc.alif.model.RestApiService

class LojistaActivity : AppCompatActivity() {
    private val viewBinding : ActivityClienteBinding by viewBinding()
    //var lojistaData: LojistaData? = null
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

                val lojistaData = this.getSharedPreferences("LojistaData", Context.MODE_PRIVATE) ?: return@getLojistaData
                with(lojistaData.edit()){
                    putInt("id_lojista",response?.id_lojista.toString().toInt())
                    putString("ocupacao",response?.ocupacao.toString())
                    putString("nome_fantasia", response?.nome_fantasia.toString())
                    putString("email", response?.email.toString())
                    putString("doc", response?.doc.toString())
                    putString("nome",response?.nome.toString())
                    apply()
                }
                val nomeFantasia = lojistaData.getString("nome_fantasia", "")
                viewBinding.nomeCliente.text = nomeFantasia
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