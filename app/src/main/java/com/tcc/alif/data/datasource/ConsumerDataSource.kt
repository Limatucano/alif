package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.MyQueuesResponse
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.model.Response
import com.tcc.alif.data.util.Constants
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
                                idQueue = queue.idQueue,
                                queueName = queue.name,
                                consumerPosition = (quantityInProgress - (index + 1)) * -1,
                                estimatedTime = queue.averageTime?.times(index - 1),
                                idUser = idUser,
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