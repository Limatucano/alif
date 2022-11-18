package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.EmployeeDataSource
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val employeeDataSource: EmployeeDataSource
) {

    fun getMyEmployee(idCompany: String) = employeeDataSource.getMyEmployee(
        idCompany = idCompany
    )
}