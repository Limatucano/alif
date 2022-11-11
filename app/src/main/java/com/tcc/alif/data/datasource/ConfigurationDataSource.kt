package com.tcc.alif.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.util.Constants.PASSWORD_UPDATED
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.await
import com.tcc.alif.data.util.requestFirebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.internal.notify
import javax.inject.Inject

class ConfigurationDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    fun updatePassword(
        newPassword: String
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))

        firebaseAuth
            .currentUser
            ?.updatePassword(newPassword)
            ?.await() ?: emit(Response.error(UNKNOWN_ERROR))

        emit(Response.Success(PASSWORD_UPDATED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)
}