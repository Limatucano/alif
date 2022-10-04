package com.tcc.alif.data.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow


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

fun <T>Task<T>.requestFirebase(
    blockToRun : CoroutineScope,
    onSuccess: ((t: T) -> Unit)? = null,
    onError: ((e: String) -> Unit)? = null,
    onLoading: ((loading: Boolean) -> Unit)? = null
){
    blockToRun.launch{
        onLoading?.invoke(true)
    }
    addOnSuccessListener {
        blockToRun.launch(Dispatchers.Main){
            onLoading?.invoke(false)
            onSuccess?.invoke(it)
        }
    }.addOnFailureListener {
        blockToRun.launch(Dispatchers.Main) {
            onLoading?.invoke(false)
            onError?.invoke(it.message ?: UNKNOWN_ERROR)
        }
    }
}
const val UNKNOWN_ERROR = "Erro desconhecido"