package com.tcc.alif.view.ui.administrator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.databinding.FragmentMainAdministratorBinding
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.configuration.ConfigurationFragment
import com.tcc.alif.view.ui.administrator.home.HomeFragment
import com.tcc.alif.view.ui.administrator.queues.QueuesFragment


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

        //TODO : Bug - When i come back from forms screen to queue screen, the bottomNavigation can't show right screen (think more about to use a side menu rather than bottom navigation)
        //TODO : refactor names and include new fragments
        //TODO : Save company in shared preferences and to remove constructor params
        val homeFragment = HomeFragment(company)
        val funcionariosLojistaFragment = HomeFragment(company)

        setupSharedPreferences(company)
        setCurrentFragment(homeFragment)
        binding.bottomNavigationView.menu.getItem(HOME_INDEX).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.funcionarios_func->setCurrentFragment(funcionariosLojistaFragment)
                R.id.filas_func->setCurrentFragment(QueuesFragment(company))
                R.id.config_func->setCurrentFragment(ConfigurationFragment(company))
                R.id.home_func->setCurrentFragment(homeFragment)
            }
            true
        }
    }

    private fun setupSharedPreferences(company: CompanyResponse){
        sharedPreferences.companyId = company.idCompany
        sharedPreferences.companyName = company.tradeName
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