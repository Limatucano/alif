package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.*
import com.tcc.alif.data.util.*
import com.tcc.alif.data.util.Constants.SUCCESSFULLY_UPDATED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AdministratorDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
){

    fun updateCallStatus(
        status: CallStatus,
        idQueue: String,
        idUser: String
    ): Flow<Response<String>> = flow{
        emit(Response.loading(true))

        getQueueCalls(
            idQueue = idQueue
        ).collect{

            val serviceUpdated = QueueResponse()
                .toQueueResponse(it.data)
                .service
                .map { service ->
                    Service(
                        enrollmentTime = service.enrollmentTime,
                        status = if(service.userId == idUser) status.value else service.status,
                        userId = service.userId
                    )
                }

            firebaseFirestore
                .collection(Constants.QUEUE_COLLECTION)
                .document(it.id)
                .update("service", serviceUpdated).await()

        }
        emit(Response.loading(false))
        emit(Response.success(SUCCESSFULLY_UPDATED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private fun getQueueCalls(idQueue: String) = flow{
        val queue = firebaseFirestore
            .collection(Constants.QUEUE_COLLECTION)
            .whereEqualTo("idQueue",idQueue)
            .get()
            .await()
            .documents[0]

        emit(queue)
    }

    fun getCallsByQueue(idQueue: String) :Flow<Response<Calls>> = flow {
        emit(Response.loading(true))
        val queue = getQueue(idQueue)

        val service = QueueResponse().toQueueResponse(
            queue.documents[0].data
        ).service

        val calls : List<Call> = service.map { service ->
                val userData = getUserData(service.userId)
                generateCall(
                    userData = userData,
                    service = service
                )
            }.sortedWith{ first, second ->
                first.enrollmentTime.compareTo(second.enrollmentTime)
            }

        val callsAdjusted = mutableListOf<Call>()
        CallStatus
            .values()
            .forEach {
                val callFiltered = getFilteredCall(
                    status = it,
                    calls = calls
                )
                callsAdjusted.addAll(callFiltered)
            }

        val call = Calls(
            calls = callsAdjusted,
            quantity = calls.size
        )
        emit(Response.success(call))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private fun getFilteredCall(status: CallStatus, calls: List<Call>) = calls.filter {
            it.status == status
        }

    private fun generateCall(userData: SigninResponse, service: Service) = Call(
        idConsumer = userData.uid,
        employeeName = "",
        employeeRole = "",
        enrollmentTime = service.enrollmentTime,
        consumerName = userData.name,
        cellphone = userData.cellphone,
        birthDate = userData.birthDate,
        cpf = userData.cpf,
        status = CallStatus.values().first{ it.value == service.status }
    )

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
                }.sortedWith { first, second ->
                    first.enrollmentTime.compareTo(second.enrollmentTime)
                },
                firstConsumers = queueResponse.service.filter{
                    it.status == CallStatus.IN_HOLD.value
                }.map { service ->
                    Service(
                        name = getUserData(service.userId).name,
                        userId = service.userId,
                        enrollmentTime = service.enrollmentTime,
                        status = service.status
                    )
                }.sortedWith { first, second ->
                    first.enrollmentTime.compareTo(second.enrollmentTime)
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