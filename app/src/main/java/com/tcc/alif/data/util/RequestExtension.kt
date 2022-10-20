package com.tcc.alif.data.util

import com.google.android.gms.tasks.Task
import com.tcc.alif.data.model.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.resumeWithException


fun <T>Flow<Response<T>>.request(
    blockToRun : CoroutineScope,
    onSuccess : ((t: T) -> Unit)? = null,
    onError : ((e : String) -> Unit)? = null,
    onLoading : ((loading : Boolean) -> Unit)? = null,
){
    blockToRun.launch {
        collect{ response ->
            when(response){
                is Response.Loading -> onLoading?.invoke(response.loading)
                is Response.Success -> onSuccess?.invoke(response.data)
                is Response.Error -> onError?.invoke(response.message ?: UNKNOWN_ERROR)
            }
        }
    }
}

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

suspend fun <T> Task<T>.await(): T {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException(
                    "Task $this was cancelled normally.")
            } else {
                result
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { block ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) block.cancel() else block.resume(result, null)
            } else {
                block.resumeWithException(e)
            }
        }
    }
}

fun <T>Task<T>.requestFirebase(
    blockToRun : CoroutineScope,
    onSuccess: ((t: T) -> Unit)? = null,
    onError: ((e: String) -> Unit)? = null,
    onLoading: ((loading: Boolean) -> Unit)? = null
){
    try {
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
    }catch (e: Exception){
        blockToRun.launch(Dispatchers.Main) {
            onLoading?.invoke(false)
            onError?.invoke(e.message ?: UNKNOWN_ERROR)
        }
    }

}
const val UNKNOWN_ERROR = "Erro desconhecido"