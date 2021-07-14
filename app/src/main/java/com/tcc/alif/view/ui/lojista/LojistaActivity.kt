package com.tcc.alif.view.ui.lojista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tcc.alif.R
import com.tcc.alif.databinding.ActivityClienteBinding

class LojistaActivity : AppCompatActivity() {
    private val viewBinding : ActivityClienteBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lojista)

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