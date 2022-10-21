package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.*
import com.tcc.alif.data.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AdministratorDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
){

    fun getCallsByQueue(idQueue: String) :Flow<Response<Calls>> = flow {
        emit(Response.loading(true))
        val queue = getQueue(idQueue)

        val service = QueueResponse().toQueueResponse(
            queue.documents[0].data
        ).service

        val calls = Calls(
            calls = service.map {
                val userData = getUserData(it.userId)
                Call(
                    idConsumer = userData.uid,
                    employeeName = "",
                    employeeRole = "",
                    consumerName = userData.name,
                    cellphone = userData.cellphone,
                    birthDate = userData.birthDate,
                    cpf = userData.cpf
                )
            },
            quantity = service.size
        )

        emit(Response.success(calls))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun getQueuesByCompany(idCompany: String) : Flow<Response<Queues>> = flow{
        emit(Response.Loading(true))
        val queues = getQueues(idCompany)

        val queuesData = Queues(
            queues = queues.map {
                QueueResponse().toQueueResponse(it.data)
            }
        )

        queuesData.queues = queuesData.queues.map { queueResponse ->
            QueueResponse(
                idQueue = queueResponse.idQueue,
                name = queueResponse.name,
                status = queueResponse.status,
                openingTime = queueResponse.openingTime,
                closingTime = queueResponse.closingTime,
                quantity = queueResponse.quantity,
                description = queueResponse.description,
                titleCategory = queueResponse.titleCategory,
                averageTime = queueResponse.averageTime,
                employeeCreator = "",
                service = queueResponse.service.map { service ->
                    Service(
                        name = getUserData(service.userId).name,
                        userId = service.userId,
                        enrollmentTime = service.enrollmentTime,
                        status = service.status
                    )
                }
            )
        }

        emit(Response.success(queuesData))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private suspend fun getQueues(idCompany: String) = firebaseFirestore
        .collection(Constants.QUEUE_COLLECTION)
        .whereEqualTo("idCompany",idCompany)
        .get()
        .await()

    private suspend fun getQueue(idQueue: String) = firebaseFirestore
        .collection(Constants.QUEUE_COLLECTION)
        .whereEqualTo("idQueue", idQueue)
        .get()
        .await()

    private suspend fun getUserData(userId: String): SigninResponse {
        val user = firebaseFirestore
            .collection(Constants.USER_COLLECTION)
            .whereEqualTo("uid", userId)
            .get()
            .await()
        val userMapped = user.documents[0].data?.let {
            SigninResponse().toSignResponse(it)
        }
        return userMapped ?: SigninResponse()
    }
}