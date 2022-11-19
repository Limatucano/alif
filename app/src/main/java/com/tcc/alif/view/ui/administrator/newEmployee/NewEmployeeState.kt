package com.tcc.alif.view.ui.administrator.newEmployee

import com.tcc.alif.data.model.SigninResponse

sealed class NewEmployeeState{
    data class Error(val message: String) : NewEmployeeState()
    data class Loading(val loading: Boolean) : NewEmployeeState()
    data class EmployeeData(val user: SigninResponse) : NewEmployeeState()
    data class EmployeeInserted(val message: String) : NewEmployeeState()
}
