package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.Queues
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.model.Service
import com.tcc.alif.data.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AdministratorDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
){

    fun getQueuesByCompany(idCompany: String) : Flow<Response<Queues>> = flow{
        emit(Response.Loading(true))
        val queues = firebaseFirestore
            .collection(Constants.QUEUE_COLLECTION)
            .whereEqualTo("idCompany",idCompany)
            .get()
            .await()

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
                        name = getUserName(service.userId),
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

    private suspend fun getUserName(userId: String): String{
        val user = firebaseFirestore
            .collection(Constants.USER_COLLECTION)
            .whereEqualTo("uid", userId)
            .get()
            .await()
        val name = user.documents[0].data?.get("name").toString().emptyIfNull()
        return name
    }
}