package com.tcc.alif.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.util.await
import javax.inject.Inject


class SignInDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun signIn(signin: Signin) : FirebaseUser {
        firebaseAuth.signInWithEmailAndPassword(
                signin.email,
                signin.password
        ).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("","")
    }

    fun getUserData(
        uid: String?
    ) : Task<QuerySnapshot>{
         return firebaseFirestore.collection("user")
            .whereEqualTo("uid", uid)
            .get()
    }
}