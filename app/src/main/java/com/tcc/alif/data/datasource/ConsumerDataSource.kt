package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.*
import com.tcc.alif.data.model.Service.Companion.modelToMap
import com.tcc.alif.data.util.Constants
import com.tcc.alif.data.util.Constants.SERVICE
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ConsumerDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val companyDataSource: CompanyDataSource
) {
    fun cancelSubscription(
        idQueue: String,
        service: Service
    ) : Flow<Response<String>> = flow {
        emit(Response.loading(true))

        val document = getQueueDocument(idQueue)
        val serviceAdjusted = service.copy(status = CallStatus.CANCELED.value)
        if(document != null){
            firebaseFirestore
                .collection(Constants.QUEUE_COLLECTION)
                .document(document)
                .update(SERVICE,FieldValue.arrayRemove(service.modelToMap()))
                .await()
            firebaseFirestore
                .collection(Constants.QUEUE_COLLECTION)
                .document(document)
                .update(SERVICE, FieldValue.arrayUnion(serviceAdjusted.modelToMap()))
                .await()
        }else{
            emit(Response.error(UNKNOWN_ERROR))
            return@flow
        }

        emit(Response.success(Constants.CANCELLED_SUBSCRIPTION))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun searchQueues(
        filter: String,
        byQrCode: Boolean
    ) : Flow<Response<List<QueueResponse>>> = flow {
        emit(Response.loading(true))

        getQueues().collect{ queues ->
            val response = if(byQrCode){
                queues.filter{
                    it.idQueue == filter
                }
            } else {
                queues.filter {
                    it.name.uppercase().startsWith(filter.uppercase())
                }
            }
            val responseMapped = response.map {
                QueueResponse(
                    idQueue = it.idQueue,
                    idCompany = it.idCompany,
                    companyName = companyDataSource.getCompany(it.idCompany).tradeName ?: "",
                    name = it.name,
                    status = it.status,
                    openingTime = it.openingTime,
                    closingTime = it.closingTime,
                    quantity = it.quantity,
                    description = it.description,
                    titleCategory = it.titleCategory,
                    employeeResponsible = it.employeeResponsible,
                    averageTime = it.averageTime,
                    employeeCreator = it.employeeCreator
                )
            }
            emit(Response.success(responseMapped))
        }
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    fun getMyQueues(
        idUser: String
    ) : Flow<Response<List<MyQueuesResponse>>> = flow {
        emit(Response.loading(true))

        getQueues().collect{ queues ->
            val myQueues = mutableListOf<MyQueuesResponse>()
            queues.forEach { queue ->
                val serviceSorted = queue.service.filter{ service ->
                    service.status == CallStatus.IN_HOLD.value || service.status == CallStatus.IN_PROGRESS.value
                }.sortedWith{ first, second ->
                    first.enrollmentTime.compareTo(second.enrollmentTime)
                }
                val quantityInProgress : Int = serviceSorted.filter { it.status ==  CallStatus.IN_PROGRESS.value}.size
                serviceSorted.forEachIndexed { index, service ->
                    if(service.userId == idUser){
                        myQueues.add(
                            MyQueuesResponse(
                                idService = service.idService,
                                idQueue = queue.idQueue,
                                queueName = queue.name,
                                consumerPosition = (quantityInProgress - (index + 1)) * -1,
                                estimatedTime = queue.averageTime?.times(index - 1),
                                idUser = idUser,
                                employeeResponsible = service.employeeResponsible,
                                enrollmentTime = service.enrollmentTime,
                                companyName = companyDataSource.getCompany(queue.idCompany).tradeName ?: "",
                                status = CallStatus.getCallStatusByValue(service.status)
                            )
                        )
                    }
                }
            }
            emit(Response.success(myQueues.toList()))
        }
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)

    private suspend fun getQueueDocument(
        idQueue: String
    ) = firebaseFirestore
        .collection(Constants.QUEUE_COLLECTION)
        .whereEqualTo(Constants.ID_QUEUE,idQueue)
        .get()
        .await()
        .documents
        .firstOrNull()
        ?.id

    private fun getQueues() : Flow<List<QueueResponse>> = flow{
        val response = firebaseFirestore
            .collection(Constants.QUEUE_COLLECTION)
            .get()
            .await()

        val queues = response.documents.map {
            QueueResponse().toQueueResponse(it.data)
        }

        emit(queues)
    }
}