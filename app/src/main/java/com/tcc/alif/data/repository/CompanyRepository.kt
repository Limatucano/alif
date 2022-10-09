package com.tcc.alif.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.tcc.alif.data.datasource.CompanyDataSource
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val dataSource: CompanyDataSource
) {

    fun getAllCompaniesByUser(idCompanies: List<String>): Task<QuerySnapshot> {
        return dataSource.getAllCompaniesByUser(idCompanies = idCompanies)
    }
}