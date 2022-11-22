package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.BusinessRequestsResponse
import com.tcc.alif.data.model.EmployeeResponse
import com.tcc.alif.data.model.EmployeeResponse.Companion.getStatus
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.model.local.Employee
import com.tcc.alif.data.model.local.EmployeeStatus
import com.tcc.alif.data.util.Constants.CPF
import com.tcc.alif.data.util.Constants.EMPLOYEE_ALREADY_EXISTS
import com.tcc.alif.data.util.Constants.EMPLOYEE_COLLECTION
import com.tcc.alif.data.util.Constants.EMPLOYEE_DELETED
import com.tcc.alif.data.util.Constants.EMPLOYEE_SUCCESSFULLY_INSERTED
import com.tcc.alif.data.util.Constants.ID_COMPANY
import com.tcc.alif.data.util.Constants.ID_USER
import com.tcc.alif.data.util.Constants.STATUS
import com.tcc.alif.data.util.Constants.BUSINESS_ADJUSTED
import com.tcc.alif.data.util.Constants.USER_COLLECTION
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class EmployeeDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val administratorDataSource: AdministratorDataSource,
    private val companyDataSource: CompanyDataSource
){

    fun updateBusinessRequest(
        businessRequest: BusinessRequestsResponse,
        newStatus: String
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))

        val document = getEmployeeDocument(
            idCompany = businessRequest.idCompany,
            idUser = businessRequest.idUser
        )
        if(document == null){
            emit(Response.error(UNKNOWN_ERROR))
        }else{
            firebaseFirestore
                .collection(EMPLOYEE_COLLECTION)
                .document(document)
                .update(STATUS, newStatus)

            emit(Response.success(BUSINESS_ADJUSTED))
        }

    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun getMyBusinessRequests(
        idUser: String
    ): Flow<Response<List<BusinessRequestsResponse>>> = flow {
        emit(Response.loading(true))

        employees(
            value = idUser,
            param = ID_USER
        ).collect{ requests ->
            val requestsFiltered = requests.filter { it.status ==  EmployeeStatus.WAITING_STATUS.value }

            val businessRequests = requestsFiltered.map { e ->
                BusinessRequestsResponse(
                    idCompany = e.idCompany,
                    idUser = e.idUser,
                    status = e.status,
                    cnpj = companyDataSource.getCompany(e.idCompany).cnpj ?: "",
                    tradeName = companyDataSource.getCompany(e.idCompany).tradeName ?: ""
                )
            }
            emit(Response.success(businessRequests))
        }

    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun getMyEmployee(
        idCompany: String
    ): Flow<Response<List<Employee>>> = flow {
        emit(Response.loading(true))

        employees(
            value = idCompany,
            param = ID_COMPANY
        ).collect{
            val users = it.map { user ->
                Employee(
                    cpf = administratorDataSource.getUserData(user.idUser).cpf,
                    name = administratorDataSource.getUserData(user.idUser).name,
                    uid = administratorDataSource.getUserData(user.idUser).uid,
                    cellphone = administratorDataSource.getUserData(user.idUser).cellphone,
                    statusRequest = user.getStatus()
                )
            }.sortedBy { employee ->
                employee.statusRequest
            }
            emit(Response.success(users))
        }
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun deleteEmployee(
        idCompany: String,
        idUser: String
    ) : Flow<Response<String>> = flow {
        emit(Response.loading(true))

        val document = getEmployeeDocument(
            idCompany = idCompany,
            idUser = idUser
        )

        if(document == null){
            emit(Response.error(UNKNOWN_ERROR))
        }else{
            firebaseFirestore
                .collection(EMPLOYEE_COLLECTION)
                .document(document)
                .delete()

            emit(Response.success(EMPLOYEE_DELETED))
        }
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun searchEmployee(
        cpf: String
    ) : Flow<Response<SigninResponse>> = flow {
        emit(Response.loading(true))

        val userTask = firebaseFirestore
            .collection(USER_COLLECTION)
            .whereEqualTo(CPF, cpf)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.data

        if(userTask == null){
            emit(Response.error(UNKNOWN_ERROR))
        }else{
            val user = SigninResponse().toSignResponse(userTask)

            emit(Response.success(user))
        }

    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun addNewEmployee(
        employee: EmployeeResponse
    ): Flow<Response<String>> = flow {
        emit(Response.loading(true))

        try {
            employees(
                value = employee.idCompany,
                param = ID_COMPANY
            ).collect{ response ->
                val employeeFiltered = response.firstOrNull {
                    it.idUser == employee.idUser
                }
                if(employeeFiltered == null){
                       firebaseFirestore
                        .collection(EMPLOYEE_COLLECTION)
                        .add(employee)
                        .await()
                    emit(Response.success(EMPLOYEE_SUCCESSFULLY_INSERTED))
                }else if(employeeFiltered != null){
                    emit(Response.error(EMPLOYEE_ALREADY_EXISTS))
                }
            }
        }catch (e: Exception){
            emit(Response.error(UNKNOWN_ERROR))
        }

    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private suspend fun getEmployeeDocument(
        idCompany: String,
        idUser: String
    ) = firebaseFirestore
            .collection(EMPLOYEE_COLLECTION)
            .whereEqualTo(ID_COMPANY, idCompany)
            .whereEqualTo(ID_USER, idUser)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.id

    private fun employees(
        value: String,
        param: String
    ) : Flow<List<EmployeeResponse>> = flow {
        val employees = firebaseFirestore
            .collection(EMPLOYEE_COLLECTION)
            .whereEqualTo(param, value)
            .get()
            .await()

        emit(employees.map { EmployeeResponse().toEmployeeResponse(it.data) })
    }
}