package com.tcc.alif.data.util

import retrofit2.Response


fun <T> Response<T>.request(
    onSuccess : ((t: T) -> Unit)? = null,
    onError : ((e : String) -> Unit)? = null,
    onLoading : ((loading : Boolean) -> Unit)? = null,
){
    onLoading?.invoke(true)
    try {
        if(this.isSuccessful && this.body() != null){
            onLoading?.invoke(false)
            onSuccess?.invoke(this.body()!!)
        }else{
            onLoading?.invoke(false)
            onError?.invoke(this.errorBody().toString())
        }

    }catch (error : Exception){
        onError?.invoke(error.message ?: "")
    }

}