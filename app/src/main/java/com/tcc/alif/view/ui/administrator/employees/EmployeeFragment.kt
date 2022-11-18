package com.tcc.alif.view.ui.administrator.employees

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.databinding.FragmentEmployeeBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeFragment : BaseFragment<FragmentEmployeeBinding>(FragmentEmployeeBinding::inflate) {

    private val viewModel: EmployeeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.employee_screen_title)
        )
        setListener()
        setObserver()
        viewModel.handleIntent(
            intent = EmployeeIntent.GetMyEmployees(
                idCompany = sharedPreferences.companyId ?: EMPTY_STRING
            )
        )
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is EmployeeState.Loading -> updateLoading(state.loading)
                is EmployeeState.Employees -> {
                    updateLoading(false)
                    //TODO: create adapter and show employees
                }
                is EmployeeState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
            }
        }
    }

    private fun updateLoading(loading: Boolean) = binding.run {
        employeeSwipe.isRefreshing = loading
    }

    private fun setListener() = binding.run {
    }
}