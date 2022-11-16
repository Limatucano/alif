package com.tcc.alif.view.ui.profile

sealed class UserProfileState{
    data class Loading(val loading: Boolean) : UserProfileState()
    data class Error(val message: String) : UserProfileState()
    data class UserDataUpdated(val message: String) : UserProfileState()
}
