package com.tcc.alif.data.model

sealed class Response {
    data class Success<T>(val data: T): Response()
    data class Error(val message: String?): Response()
    data class Loading(val loading: Boolean): Response()
}