package com.tcc.alif.view.ui.administrator.employees

import com.tcc.alif.data.model.SigninResponse

sealed class EmployeeState{
    data class Error(val message: String) : EmployeeState()
    data class Loading(val loading: Boolean) : EmployeeState()
    data class Employees(val employees: List<SigninResponse>) : EmployeeState()
}
