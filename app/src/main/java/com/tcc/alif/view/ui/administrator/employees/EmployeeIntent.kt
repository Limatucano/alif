package com.tcc.alif.view.ui.administrator.employees

sealed class EmployeeIntent{
    data class GetMyEmployees(val idCompany: String) : EmployeeIntent()
    data class DeleteEmployee(
        val idCompany: String,
        val idUser: String
    ) : EmployeeIntent()
}
