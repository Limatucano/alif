package com.tcc.alif.view.ui.administrator.employees

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.CompanyRepository
import com.tcc.alif.data.repository.EmployeeRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {

    val state = MutableLiveData<EmployeeState>()

    fun handleIntent(intent: EmployeeIntent){
        when(intent){
            is EmployeeIntent.GetMyEmployees -> getMyEmployees(intent.idCompany)
            is EmployeeIntent.DeleteEmployee -> deleteEmployee(
                idCompany = intent.idCompany,
                idUser = intent.idUser
            )
        }
    }

    private fun deleteEmployee(
        idCompany: String,
        idUser: String
    ){
        viewModelScope.launch {
            employeeRepository.deleteEmployee(
                idUser = idUser,
                idCompany = idCompany
            ).request(
                blockToRun = this,
                onError = { state.postValue(EmployeeState.Error(it)) },
                onLoading = { state.postValue(EmployeeState.Loading(it)) },
                onSuccess = {
                    companyRepository.removeCompany(
                        idUser = idUser,
                        idCompany = idCompany
                    ).request(
                        blockToRun = this,
                        onError = { removeCompany ->
                            state.postValue(EmployeeState.Error(removeCompany))
                        },
                        onLoading = { removeCompany ->
                            state.postValue(EmployeeState.Loading(removeCompany))
                        },
                        onSuccess = { _ ->
                            state.postValue(EmployeeState.EmployeeDeleted(it))
                        }
                    )
                }
            )
        }
    }

    private fun getMyEmployees(
        idCompany: String
    ){
        viewModelScope.launch {
            employeeRepository.getMyEmployee(idCompany).request(
                blockToRun = this,
                onError = { state.postValue(EmployeeState.Error(it)) },
                onLoading = { state.postValue(EmployeeState.Loading(it)) },
                onSuccess = { state.postValue(EmployeeState.Employees(it)) }
            )
        }
    }
}