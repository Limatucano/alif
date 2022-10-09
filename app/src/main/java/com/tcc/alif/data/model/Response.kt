package com.tcc.alif.data.model

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val message: String?) : Response<T>()
    data class Loading<T>(val loading: Boolean) : Response<T>()

    companion object {
        fun <T> loading(loading: Boolean) = Loading<T>(loading)
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }
}