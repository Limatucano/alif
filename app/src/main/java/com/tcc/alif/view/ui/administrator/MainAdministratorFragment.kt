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

        val homeFragment = HomeFragment()

        setupSharedPreferences(company)
        setCurrentFragment(getCurrentFragment().first)
        binding.bottomNavigationView.menu.getItem(getCurrentFragment().second).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.funcionarios_func->{
                    sharedPreferences.lastScreen = EmployeeFragment().javaClass.simpleName
                    setCurrentFragment(EmployeeFragment())
                }
                R.id.filas_func->{
                    sharedPreferences.lastScreen = QueuesFragment().javaClass.simpleName
                    setCurrentFragment(QueuesFragment())
                }
                R.id.config_func->{
                    sharedPreferences.lastScreen = ConfigurationFragment().javaClass.simpleName
                    setCurrentFragment(ConfigurationFragment())
                }
                R.id.home_func->{
                    sharedPreferences.lastScreen = HomeFragment().javaClass.simpleName
                    setCurrentFragment(homeFragment)
                }
            }
            true
        }
    }

    private fun getCurrentFragment() : Pair<Fragment, Int>{
        return when(sharedPreferences.lastScreen){
            "EmployeeFragment" -> Pair(EmployeeFragment(), EMPLOYEE_INDEX)
            "QueuesFragment" -> Pair(QueuesFragment(), QUEUES_INDEX)
            "HomeFragment" -> Pair(HomeFragment(), HOME_INDEX)
            "ConfigurationFragment" -> Pair(ConfigurationFragment(), CONFIGURATION_INDEX)
            else -> Pair(HomeFragment(), HOME_INDEX)
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
        userRole = company.role
    }

    private fun setCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            setCustomAnimations(
                R.anim.nav_default_enter_anim,
                R.anim.nav_default_exit_anim
            )
            commit()
        }

    companion object{
        const val EMPLOYEE_INDEX = 0
        const val QUEUES_INDEX = 1
        const val HOME_INDEX = 2
        const val CONFIGURATION_INDEX = 3
    }
}