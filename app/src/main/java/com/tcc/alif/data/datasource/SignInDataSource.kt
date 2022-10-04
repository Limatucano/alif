package com.tcc.alif.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.model.SigninResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SignInDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    fun signIn(signin: Signin) : Flow<Task<AuthResult>> = flow{
        emit(
            firebaseAuth.signInWithEmailAndPassword(
                signin.email,
                signin.password
            )
        )
    }

    fun getUserData(
        uid: String?
    ) : Flow<Task<QuerySnapshot>> = flow{
        emit(
            firebaseFirestore.collection("person")
            .whereEqualTo("uid", uid)
            .get()
        )
    }
}