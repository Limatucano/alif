package com.tcc.alif.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tcc.alif.data.util.Constants.COMPANY_COLLECTION
import javax.inject.Inject

class CompanyDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    fun getAllCompaniesByUser(idCompanies: List<String>) : Task<QuerySnapshot>{
        return firebaseFirestore
            .collection(COMPANY_COLLECTION)
            .whereIn("idCompany", idCompanies)
            .get()
    }
}