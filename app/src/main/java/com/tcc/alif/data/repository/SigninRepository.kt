package com.tcc.alif.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QuerySnapshot
import com.tcc.alif.data.datasource.SignInDataSource
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.model.Signin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SigninRepository {
    suspend fun signin(signin : Signin) : Flow<Task<AuthResult>>
    fun getUserData(uid: String?) : Flow<Task<QuerySnapshot>>
}

class SigninRepositoryImpl @Inject constructor(
    private val dataSource: SignInDataSource
) : SigninRepository{

    override suspend fun signin(signin: Signin): Flow<Task<AuthResult>> {
        return dataSource.signIn(signin = signin)
    }

    override fun getUserData(uid: String?): Flow<Task<QuerySnapshot>>{
        return dataSource.getUserData(uid = uid)
    }
}