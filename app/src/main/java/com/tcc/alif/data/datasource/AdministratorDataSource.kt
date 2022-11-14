package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.*
import com.tcc.alif.data.model.QueueRequest.Companion.modelToMap
import com.tcc.alif.data.util.Constants
import com.tcc.alif.data.util.Constants.ID_COMPANY
import com.tcc.alif.data.util.Constants.ID_QUEUE
import com.tcc.alif.data.util.Constants.QUEUE_COLLECTION
import com.tcc.alif.data.util.Constants.QUEUE_SUCCESSFULLY_INSERTED
import com.tcc.alif.data.util.Constants.QUEUE_SUCCESSFULLY_UPDATED
import com.tcc.alif.data.util.Constants.SUCCESSFULLY_UPDATED
import com.tcc.alif.data.util.Constants.UID
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AdministratorDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
){

    fun updateQueue(queue: QueueRequest): Flow<Response<String>> = flow{
        emit(Response.loading(true))

        val documentQueue : String? = getQueue(queue.idQueue)
            .documents
            .firstOrNull()?.id

        if(documentQueue != null){
            firebaseFirestore
                .collection(QUEUE_COLLECTION)
                .document(documentQueue)
                .update(queue.modelToMap())
                .await()
        }else{
            emit(Response.error(UNKNOWN_ERROR))
            return@flow
        }

        emit(Response.loading(false))
        emit(Response.success(QUEUE_SUCCESSFULLY_UPDATED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun saveNewQueue(queue: QueueRequest): Flow<Response<String>> = flow{
        emit(Response.loading(true))

        firebaseFirestore
            .collection(QUEUE_COLLECTION)
            .add(queue)
            .await()

        emit(Response.loading(false))
        emit(Response.success(QUEUE_SUCCESSFULLY_INSERTED))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

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
                .collection(QUEUE_COLLECTION)
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
            .collection(QUEUE_COLLECTION)
            .whereEqualTo(ID_QUEUE,idQueue)
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

    fun getQueuesByCompany(
        idCompany: String,
        filter: String = ""
    ) : Flow<Response<Queues>> = flow{
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

        if(filter.isNotEmpty() || filter.isNotBlank()){
            queuesData.queues = queuesData.queues.filter {
                it.name.uppercase().startsWith(filter.uppercase())
            }
        }

        emit(Response.success(queuesData))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private suspend fun getQueues(idCompany: String) = firebaseFirestore
        .collection(QUEUE_COLLECTION)
        .whereEqualTo(ID_COMPANY,idCompany)
        .get()
        .await()

    private suspend fun getQueue(idQueue: String) = firebaseFirestore
        .collection(QUEUE_COLLECTION)
        .whereEqualTo(ID_QUEUE, idQueue)
        .get()
        .await()

    private suspend fun getUserData(userId: String): SigninResponse {
        val user = firebaseFirestore
            .collection(Constants.USER_COLLECTION)
            .whereEqualTo(UID, userId)
            .get()
            .await()
        val userMapped = user.documents[0].data?.let {
            SigninResponse().toSignResponse(it)
        }
        return userMapped ?: SigninResponse()
    }
}