package com.tcc.alif.view.ui.administrator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.databinding.FragmentMainAdministratorBinding
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.home.HomeFragment


class MainAdministratorFragment : BaseFragment<FragmentMainAdministratorBinding>(FragmentMainAdministratorBinding::inflate) {

    private lateinit var company : CompanyResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        val args = MainAdministratorFragmentArgs.fromBundle(bundle)
        company = args.company
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO : to refactor this feature, because has deprecated methods
        //TODO : refactor names and include new fragments
        val homeFragment = HomeFragment(company)
        val funcionariosLojistaFragment = HomeFragment(company)
        val filasLojistaFragment = HomeFragment(company)
        val perfilLojistaFragment = HomeFragment(company)
        val configLojistaFragment = HomeFragment(company)

        setCurrentFragment(homeFragment)
        binding.bottomNavigationView.menu.getItem(HOME_INDEX).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.funcionarios_func->setCurrentFragment(funcionariosLojistaFragment)
                R.id.filas_func->setCurrentFragment(filasLojistaFragment)
                R.id.config_func->setCurrentFragment(configLojistaFragment)
                R.id.perfil_func->setCurrentFragment(perfilLojistaFragment)
                R.id.home_func->setCurrentFragment(homeFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    companion object{
        const val HOME_INDEX = 2
    }

}