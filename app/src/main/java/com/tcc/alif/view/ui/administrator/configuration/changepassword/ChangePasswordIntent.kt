package com.tcc.alif.view.ui.administrator.configuration.changepassword

sealed class ChangePasswordIntent{
    data class UpdatePassword(val newPassword: String): ChangePasswordIntent()
    data class ValidatePassword(
        val validatePassword: String,
        val currentPassword: String,
        val newPassword: String,
        val confirmPassword: String
    ) : ChangePasswordIntent()
}
