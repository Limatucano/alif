package com.tcc.alif.view.ui


sealed class BaseState{
    data class Success<T>(val response : T) : BaseState()
    data class Loading(val loading: Boolean) : BaseState()
    data class Error(val message: String) : BaseState()
}
