package com.tcc.alif.view.ui.login

import com.tcc.alif.data.model.SigninResponse

sealed class SigninState{
    data class SuccessSignin(val response : SigninResponse) : SigninState()
    data class Loading(val loading : Boolean) : SigninState()
    data class Error(val message : String) : SigninState()
}
