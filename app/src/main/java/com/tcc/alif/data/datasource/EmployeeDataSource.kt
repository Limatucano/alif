package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.EmployeeResponse
import com.tcc.alif.data.model.EmployeeResponse.Companion.ACCEPTED_STATUS
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.util.Constants.EMPLOYEE_COLLECTION
import com.tcc.alif.data.util.Constants.ID_COMPANY
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class EmployeeDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val administratorDataSource: AdministratorDataSource
){

    fun getMyEmployee(
        idCompany: String
    ): Flow<Response<List<SigninResponse>>> = flow {
        emit(Response.loading(true))

        employees(idCompany).collect{
            val users = it.filter {
                    employee -> employee.status == ACCEPTED_STATUS
            }.map { user ->
                SigninResponse(
                    cpf = administratorDataSource.getUserData(user.idUser).cpf,
                    name = administratorDataSource.getUserData(user.idUser).name,
                    uid = administratorDataSource.getUserData(user.idUser).uid,
                    cellphone = administratorDataSource.getUserData(user.idUser).cellphone
                )
            }
            emit(Response.success(users))
        }
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private fun employees(
        idCompany: String
    ) : Flow<List<EmployeeResponse>> = flow {
        val employees = firebaseFirestore
            .collection(EMPLOYEE_COLLECTION)
            .whereEqualTo(ID_COMPANY, idCompany)
            .get()
            .await()

        emit(employees.map { EmployeeResponse().toEmployeeResponse(it.data) })
    }
}