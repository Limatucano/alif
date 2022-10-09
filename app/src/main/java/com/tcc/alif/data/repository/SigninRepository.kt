package com.tcc.alif.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.tcc.alif.data.datasource.SignInDataSource
import com.tcc.alif.data.model.Signin
import javax.inject.Inject

interface SigninRepository {
    suspend fun signin(signin : Signin) : FirebaseUser?
    fun getUserData(uid: String?) : Task<QuerySnapshot>
}

class SigninRepositoryImpl @Inject constructor(
    private val dataSource: SignInDataSource
) : SigninRepository{

    override suspend fun signin(signin: Signin): FirebaseUser? {
        return dataSource.signIn(signin = signin)
    }

    override fun getUserData(uid: String?): Task<QuerySnapshot> {
        return dataSource.getUserData(uid = uid)
    }
}