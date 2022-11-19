package com.tcc.alif.view.ui.administrator.newEmployee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.EmployeeRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewEmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel(){

    val state = MutableLiveData<NewEmployeeState>()

    fun handleIntent(intent: NewEmployeeIntent){
        when(intent){
            is NewEmployeeIntent.SearchEmployee -> searchEmployee(intent.cpf)
        }
    }

    private fun searchEmployee(cpf: String){
        viewModelScope.launch {
            employeeRepository.searchEmployee(cpf).request(
                blockToRun = this,
                onError = { state.postValue(NewEmployeeState.Error(it)) },
                onLoading = { state.postValue(NewEmployeeState.Loading(it)) },
                onSuccess = { state.postValue(NewEmployeeState.EmployeeData(it)) }
            )
        }
    }
}