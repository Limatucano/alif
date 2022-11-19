package com.tcc.alif.view.ui.administrator.newEmployee

import com.tcc.alif.data.model.EmployeeResponse

sealed class NewEmployeeIntent{
    data class SearchEmployee(val cpf: String): NewEmployeeIntent()
    data class InsertEmployee(val employee: EmployeeResponse): NewEmployeeIntent()
}
