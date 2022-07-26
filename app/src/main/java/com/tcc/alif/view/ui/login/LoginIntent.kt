package com.tcc.alif.view.ui.login

sealed class LoginIntent {

    data class OnSucess<T>(val data : T) : LoginIntent()
    data class Error(val message : String) : LoginIntent()
}