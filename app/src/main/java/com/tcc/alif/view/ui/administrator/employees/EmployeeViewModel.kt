package com.tcc.alif.view.ui.administrator.employees

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.EmployeeRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    val state = MutableLiveData<EmployeeState>()

    fun handleIntent(intent: EmployeeIntent){
        when(intent){
            is EmployeeIntent.GetMyEmployees -> getMyEmployees(intent.idCompany)
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