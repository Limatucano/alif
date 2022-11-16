package com.tcc.alif.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.data.model.CategoryResponse.Companion.modelToMap
import com.tcc.alif.data.model.QueueRequest.Companion.modelToMap
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.model.SigninResponse.Companion.modelToMap
import com.tcc.alif.data.util.Constants
import com.tcc.alif.data.util.Constants.CATEGORY_COLLECTION
import com.tcc.alif.data.util.Constants.CATEGORY_DELETED
import com.tcc.alif.data.util.Constants.CATEGORY_INSERTED
import com.tcc.alif.data.util.Constants.ID_COMPANY
import com.tcc.alif.data.util.Constants.PASSWORD_UPDATED
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

class ConfigurationDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    fun signOut() = firebaseAuth.signOut()

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

    fun getAllCategories(
        idCompany: String
    ): Flow<Response<List<CategoryResponse>>> = flow {
        emit(Response.loading(true))
       val categories =  firebaseFirestore
            .collection(CATEGORY_COLLECTION)
            .whereEqualTo(ID_COMPANY,idCompany)
            .get()
            .await()

       val categoriesResponse = categories.map {
           CategoryResponse().toCategoryResponse(it.data)
       }

       emit(Response.success(categoriesResponse))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun deleteCategory(
        idCategory: String
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))

        val categoryDocument = getDocumentCategory(idCategory)

        if(categoryDocument != null){
            firebaseFirestore
                .collection(CATEGORY_COLLECTION)
                .document(categoryDocument)
                .delete()
        }else{
            emit(Response.error(UNKNOWN_ERROR))
            return@flow
        }
        emit(Response.success(CATEGORY_DELETED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun insertNewCategory(
        category: CategoryResponse
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))

        firebaseFirestore
            .collection(CATEGORY_COLLECTION)
            .add(category)

        emit(Response.success(CATEGORY_INSERTED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private suspend fun getDocumentCategory(
        idCategory: String
    ) = firebaseFirestore
            .collection(CATEGORY_COLLECTION)
            .whereEqualTo(UID,idCategory)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.id

    fun editCategory(
        category: CategoryResponse
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))

        val documentCategory = getDocumentCategory(category.uid)

        if(documentCategory != null){
            firebaseFirestore
                .collection(Constants.CATEGORY_COLLECTION)
                .document(documentCategory)
                .update(category.modelToMap())
                .await()
        }else{
            emit(Response.error(UNKNOWN_ERROR))
            return@flow
        }

        emit(Response.success(Constants.CATEGORY_UPDATED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun updateDataUser(
        userData: SigninResponse
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))

        val document = getUserDocument(userData.uid)

        if(document != null){
            firebaseFirestore
                .collection(USER_COLLECTION)
                .document(document)
                .update(userData.modelToMap())
                .await()
        }else{
            emit(Response.error(UNKNOWN_ERROR))
            return@flow
        }

        emit(Response.success(Constants.USER_UPDATED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private suspend fun getUserDocument(
        idUser: String
    ) = firebaseFirestore
        .collection(USER_COLLECTION)
        .whereEqualTo(UID,idUser)
        .get()
        .await()
        .documents
        .firstOrNull()
        ?.id
}