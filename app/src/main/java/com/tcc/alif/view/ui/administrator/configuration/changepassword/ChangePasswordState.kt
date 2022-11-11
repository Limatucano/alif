package com.tcc.alif.view.ui.administrator.configuration.changepassword

sealed class ChangePasswordState{
    data class Error(val message: String) : ChangePasswordState()
    data class PasswordUpdated(val message: String) : ChangePasswordState()
    data class Loading(val loading: Boolean) : ChangePasswordState()
}
