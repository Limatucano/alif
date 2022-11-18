package com.tcc.alif.view.ui.administrator.employees

sealed class EmployeeIntent{
    data class GetMyEmployees(val idCompany: String) : EmployeeIntent()
}
