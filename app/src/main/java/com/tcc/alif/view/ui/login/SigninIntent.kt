package com.tcc.alif.view.ui.login

import com.tcc.alif.data.model.Signin as userData

sealed class SigninIntent{
    data class Signin(val signin: userData) : SigninIntent()
}
