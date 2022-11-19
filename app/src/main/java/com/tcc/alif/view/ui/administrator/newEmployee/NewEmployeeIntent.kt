package com.tcc.alif.view.ui.administrator.newEmployee

sealed class NewEmployeeIntent{
    data class SearchEmployee(val cpf: String): NewEmployeeIntent()
}
