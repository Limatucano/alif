package com.tcc.alif.view.ui.profile

import com.tcc.alif.data.model.SigninResponse

sealed class UserProfileState{
    data class Loading(val loading: Boolean) : UserProfileState()
    data class Error(val message: String) : UserProfileState()
}
