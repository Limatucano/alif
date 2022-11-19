package com.tcc.alif.view.ui.administrator.newEmployee

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.tcc.alif.R
import com.tcc.alif.data.model.EmployeeResponse
import com.tcc.alif.data.model.EmployeeResponse.Companion.WAITING_STATUS
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.util.MaskUtils.CPF_MASK
import com.tcc.alif.data.util.emptyIfNull
import com.tcc.alif.data.util.setVisible
import com.tcc.alif.databinding.FragmentNewEmployeeBinding
import com.tcc.alif.view.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewEmployeeFragment : BaseFragment<FragmentNewEmployeeBinding>(FragmentNewEmployeeBinding::inflate) {

    private val viewModel: NewEmployeeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            title = getString(R.string.employee_screen_new_title)
        )

        setListener()
        setObserver()
        setView()
    }

    private fun setView() = binding.run{
        searchField.addTextChangedListener(CPF_MASK)
    }

    private fun setObserver(){
        viewModel.state.observe(viewLifecycleOwner){ state ->
            when(state){
                is NewEmployeeState.Loading -> {
                    updateLoading(state.loading)
                }
                is NewEmployeeState.EmployeeData -> {
                    updateLoading(false)
                    setEmployeeView(
                        user = state.user,
                        show = true
                    )
                }
                is NewEmployeeState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    setEmployeeView(
                        show = false
                    )
                    updateLoading(false)
                }
                is NewEmployeeState.EmployeeInserted -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    updateLoading(false)
                }
            }
        }
    }

    private fun setEmployeeView(
        user: SigninResponse? = null,
        show: Boolean
    ) = binding.run {
        employeeContainer.setVisible(show)

        if(user != null){
            employeeName.text = user.name
        }

        employeeContainer.setOnClickListener {
            viewModel.handleIntent(NewEmployeeIntent.InsertEmployee(
                employee = EmployeeResponse(
                    idCompany = sharedPreferences.companyId.toString().emptyIfNull(),
                    idUser = user?.uid.toString().emptyIfNull(),
                    status = WAITING_STATUS
                )
            ))
        }
    }

    private fun updateLoading(loading: Boolean) = binding.run{
        newEmployeeSwipe.isRefreshing = loading
    }

    private fun setListener() = binding.run {
        searchField.setOnQueryTextListener(
            onTextChanged = {
                viewModel.handleIntent(NewEmployeeIntent.SearchEmployee(it))
            },
            onSubmitClicked = {
                viewModel.handleIntent(NewEmployeeIntent.SearchEmployee(it))
            }
        )
    }
}