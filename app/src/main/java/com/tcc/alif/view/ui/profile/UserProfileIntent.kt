package com.tcc.alif.view.ui.profile

import com.tcc.alif.data.model.SigninResponse

sealed class UserProfileIntent{
    data class UpdateUserData(val data: SigninResponse): UserProfileIntent()
}
