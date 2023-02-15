package com.tcc.alif.data.datasource

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.api.CepService
import com.tcc.alif.data.model.CompanyResponse
import com.tcc.alif.data.model.CompanyResponse.Companion.modelToMap
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.util.Constants
import com.tcc.alif.data.util.Constants.COMPANIES
import com.tcc.alif.data.util.Constants.COMPANY_COLLECTION
import com.tcc.alif.data.util.Constants.ID_COMPANY
import com.tcc.alif.data.util.Constants.UID
import com.tcc.alif.data.util.Constants.USER_COLLECTION
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CompanyDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val cepService: CepService
) {

    suspend fun getAddress(cep: String) = cepService.getAddress(cep)

    fun getAllCompaniesByUser(idUser: String) : Flow<Response<List<CompanyResponse>>> = flow{
        emit(Response.Loading(true))
        getCompaniesID(idUser).collect{
            val userResponse = SigninResponse().toSignResponse(it.documents[0].data)
            val companiesToSearch = userResponse.companies.filter { filter -> filter.companyId.isNotEmpty() }.map { map -> map.companyId }
            val response = firebaseFirestore
                .collection(COMPANY_COLLECTION)
                .whereIn("idCompany",companiesToSearch)
                .get()
                .await()
            val companies = response.map { map -> CompanyResponse().toCompanyResponse(map = map.data) }
            val companiesWithRole = companies.map { company ->
                company.copy(
                    role = userResponse.companies.firstOrNull{ user ->
                        user.companyId == company.idCompany
                    }?.role ?: ""
                )
            }
            this.emit(Response.Success(companiesWithRole))
        }
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun removeCompany(
        idUser: String,
        idCompany: String
    ) : Flow<Response<String>> = flow {
        emit(Response.Loading(true))
        getUserDocument(
            idUser = idUser
        ).collect {
            firebaseFirestore
                .collection(USER_COLLECTION)
                .document(it)
                .update("companies", FieldValue.arrayRemove(idCompany))
                .await()
            this.emit(Response.Success(""))
        }
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private fun getCompaniesID(idUser: String) = flow{
        val companiesRef = firebaseFirestore
            .collection(USER_COLLECTION)
            .whereEqualTo("uid", idUser)
            .get()
            .await()
        emit(companiesRef)
    }

    fun saveNewCompany(
        company: CompanyResponse,
        idUser: String
    ): Flow<Response<DocumentReference>> = flow{
        emit(Response.Loading(true))

        val companyRef = firebaseFirestore
            .collection(COMPANY_COLLECTION)
            .add(company)
            .await()

        getUserDocument(
            idUser = idUser
        ).collect{
            firebaseFirestore
                .collection(USER_COLLECTION)
                .document(it)
                .update(
                    COMPANIES,
                    FieldValue.arrayUnion(
                        mapOf(
                            "companyId" to company.idCompany,
                            "role" to company.role
                        )
                    )
                ).await()
        }

        emit(Response.loading(false))
        emit(Response.success(companyRef))

    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun updateCompany(
        company: CompanyResponse,
        idCompany: String
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))
        val document = getCompanyDocument(idCompany)

        if(document != null){
            firebaseFirestore
                .collection(COMPANY_COLLECTION)
                .document(document)
                .update(company.modelToMap())
                .await()
        }else{
            emit(Response.error(UNKNOWN_ERROR))
            return@flow
        }

        emit(Response.success(Constants.COMPANY_UPDATED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private suspend fun getCompanyDocument(
        idCompany: String
    ) = firebaseFirestore
        .collection(COMPANY_COLLECTION)
        .whereEqualTo(ID_COMPANY,idCompany)
        .get()
        .await()
        .documents
        .firstOrNull()
        ?.id

    private fun getUserDocument(idUser: String) = flow{
        val userRef = firebaseFirestore
            .collection(USER_COLLECTION)
            .whereEqualTo(UID,idUser)
            .get()
            .await()
            .documents[0]
            .id

        emit(userRef)
    }

    suspend fun getCompany(idCompany: String): CompanyResponse {
        val response = firebaseFirestore
            .collection(COMPANY_COLLECTION)
            .whereEqualTo(ID_COMPANY, idCompany)
            .get()
            .await()
        val responseMapped = response.documents[0].data?.let {
            CompanyResponse().toCompanyResponse(it)
        }
        return responseMapped ?: CompanyResponse()
    }
}