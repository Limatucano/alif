package com.tcc.alif.view.ui.lojista

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tcc.alif.R
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.databinding.FragmentMainAdministratorBinding
import com.tcc.alif.view.ui.BaseFragment


class MainAdministratorFragment : BaseFragment<FragmentMainAdministratorBinding>(FragmentMainAdministratorBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO : to refactor this feature, because has deprecated methods
        //TODO : refactor names and include new fragments
        val homeLojistaFragment = HomeLojistaFragment()
        val funcionariosLojistaFragment = HomeLojistaFragment()
        val filasLojistaFragment = HomeLojistaFragment()
        val perfilLojistaFragment = HomeLojistaFragment()
        val configLojistaFragment = HomeLojistaFragment()

        setCurrentFragment(homeLojistaFragment)
        binding.bottomNavigationView.menu.getItem(2).isChecked = true
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.funcionarios_func->setCurrentFragment(funcionariosLojistaFragment)
                R.id.filas_func->setCurrentFragment(filasLojistaFragment)
                R.id.config_func->setCurrentFragment(configLojistaFragment)
                R.id.perfil_func->setCurrentFragment(perfilLojistaFragment)
                R.id.home_func->setCurrentFragment(homeLojistaFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = fragmentManager?.beginTransaction()?.apply {
        replace(R.id.flFragment, fragment)
        commit()
    }

}