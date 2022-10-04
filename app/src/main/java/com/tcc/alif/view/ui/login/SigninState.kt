package com.tcc.alif.view.ui.login

sealed class SigninState{
    data class SuccessSignin(val userId: String?) : SigninState()
    data class Loading(val loading : Boolean) : SigninState()
    data class Error(val message : String) : SigninState()
}
