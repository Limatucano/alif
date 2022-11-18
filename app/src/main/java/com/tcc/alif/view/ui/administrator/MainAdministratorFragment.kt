package com.tcc.alif.view.ui.administrator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tcc.alif.R
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.databinding.FragmentMainAdministratorBinding
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.configuration.ConfigurationFragment
import com.tcc.alif.view.ui.administrator.employees.EmployeeFragment
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
        val homeFragment = HomeFragment(company)

        setupSharedPreferences(company)
        setCurrentFragment(homeFragment)
        binding.bottomNavigationView.menu.getItem(HOME_INDEX).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.funcionarios_func->setCurrentFragment(EmployeeFragment())
                R.id.filas_func->setCurrentFragment(QueuesFragment())
                R.id.config_func->setCurrentFragment(ConfigurationFragment())
                R.id.home_func->setCurrentFragment(homeFragment)
            }
            true
        }
    }

    private fun setupSharedPreferences(company: CompanyResponse) = sharedPreferences.run{
        companyId = company.idCompany
        companyName = company.tradeName
        companyCategory = company.category
        companyDocument = company.cnpj
        companyOwner = company.ownerName
        companyState = company.state
        companyZipCode = company.zipCode
        companyTelephone = company.telephone
        companyStreet = company.street
        companyDistrict = company.district
        companyNumber = company.numberHouse
        companyCity = company.city
        companyAddressContinued = company.addressContinued
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