package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.EmployeeDataSource
import com.tcc.alif.data.model.EmployeeResponse
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val employeeDataSource: EmployeeDataSource
) {

    fun getMyBusinessRequests(idUser: String) = employeeDataSource.getMyBusinessRequests(
        idUser = idUser
    )

    fun getMyEmployee(idCompany: String) = employeeDataSource.getMyEmployee(
        idCompany = idCompany
    )

    fun deleteEmployee(
        idCompany: String,
        idUser: String
    ) = employeeDataSource.deleteEmployee(
        idCompany = idCompany,
        idUser = idUser
    )

    fun searchEmployee(
        cpf: String
    ) = employeeDataSource.searchEmployee(
        cpf = cpf
    )

    fun addNewEmployee(
        employee: EmployeeResponse
    ) = employeeDataSource.addNewEmployee(
        employee = employee
    )
}