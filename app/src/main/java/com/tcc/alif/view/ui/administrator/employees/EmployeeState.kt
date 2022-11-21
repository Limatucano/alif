package com.tcc.alif.view.ui.administrator.employees

import com.tcc.alif.data.model.local.Employee

sealed class EmployeeState{
    data class Error(val message: String) : EmployeeState()
    data class Loading(val loading: Boolean) : EmployeeState()
    data class Employees(val employees: List<Employee>) : EmployeeState()
    data class EmployeeDeleted(val message: String) : EmployeeState()
}
