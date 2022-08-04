package com.tcc.alif.data.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response


fun <T> CoroutineScope.request(
    blockToRun : suspend  CoroutineScope.() -> T,
    onSuccess : ((t: T) -> Unit)? = null,
    onError : ((e : String) -> Unit)? = null,
    onLoading : ((loading : Boolean) -> Unit)? = null,
){
    launch{
        onLoading?.invoke(true)
        try {
            val result = blockToRun()
            onLoading?.invoke(false)
            onSuccess?.invoke(result)
        }catch (error : Exception){
            onLoading?.invoke(false)
            onError?.invoke(error.message ?: "")
        }
    }


}