package com.tcc.alif.view.ui.administrator.configuration.changepassword

sealed class ChangePasswordIntent{
    data class UpdatePassword(val newPassword: String): ChangePasswordIntent()
}
