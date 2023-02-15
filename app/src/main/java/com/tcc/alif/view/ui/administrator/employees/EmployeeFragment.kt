package com.tcc.alif.view.ui.administrator.employees

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcc.alif.R
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.EMPTY_STRING
import com.tcc.alif.data.model.CompanyRole
import com.tcc.alif.data.model.local.Employee
import com.tcc.alif.data.util.Constants
import com.tcc.alif.data.util.setLinearLayout
import com.tcc.alif.databinding.FragmentEmployeeBinding
import com.tcc.alif.view.base.TwoOptionsBottomDialog
import com.tcc.alif.view.ui.BaseFragment
import com.tcc.alif.view.ui.administrator.MainAdministratorFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeFragment : BaseFragment<FragmentEmployeeBinding>(FragmentEmployeeBinding::inflate) {

    private val viewModel: EmployeeViewModel by viewModels()

    private val employeesAdapter by lazy {
        EmployeeAdapter(
            context = requireContext(),
            action = { showTwoOptionDialog(it) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.employee_screen_title)
        )
        setListener()
        setViews()
        setObserver()
        getAllEmployees()
    }

    private fun setViews() = binding.run{
        rvMyEmployees.adapter = employeesAdapter
        rvMyEmployees.setLinearLayout(
            context = requireContext(),
            orientation = LinearLayoutManager.VERTICAL,
            reverseLayout = false,
            withItemDecoration = true
        )
    }

    private fun showTwoOptionDialog(employee: Employee){
        TwoOptionsBottomDialog(
            context = requireContext(),
            message = getString(R.string.employee_screen_dialog_message),
            saveText = R.string.yes,
            cancelText = R.string.no,
            saveAction = {
                deleteEmployee(employee)
            }
        ).show()
    }

    private fun deleteEmployee(employee: Employee){
        if(employee.uid != sharedPreferences.userId){
            viewModel.handleIntent(
                intent = EmployeeIntent.DeleteEmployee(
                    idCompany = sharedPreferences.companyId ?: EMPTY_STRING,
                    idUser = employee.uid
                )
            )
        } else{
            Toast.makeText(requireContext(), getString(R.string.delete_employee_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is EmployeeState.Loading -> updateLoading(state.loading)
                is EmployeeState.Employees -> {
                    updateLoading(false)
                    employeesAdapter.canBeRemoved = CompanyRole.getRoleByValue(sharedPreferences.userRole).permissions[Constants.PERMISSION_REMOVE_EMPLOYEE] == true
                    employeesAdapter.employees = state.employees
                }
                is EmployeeState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
                is EmployeeState.EmployeeDeleted -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                    getAllEmployees()
                }
            }
        }
    }

    private fun getAllEmployees(){
        viewModel.handleIntent(
            intent = EmployeeIntent.GetMyEmployees(
                idCompany = sharedPreferences.companyId ?: EMPTY_STRING
            )
        )
    }

    private fun updateLoading(loading: Boolean) = binding.run {
        employeeSwipe.isRefreshing = loading
    }

    private fun setListener() = binding.run {
        addEmployee.setOnClickListener {
            requireView().findNavController().navigate(MainAdministratorFragmentDirections.actionMainAdministratorFragmentToNewEmployeeFragment())
        }
    }
}