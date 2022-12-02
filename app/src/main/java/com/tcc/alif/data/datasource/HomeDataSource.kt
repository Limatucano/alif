package com.tcc.alif.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.model.*
import com.tcc.alif.data.util.Constants.QUEUE_COLLECTION
import com.tcc.alif.data.util.DateFormats
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.await
import com.tcc.alif.data.util.toStringDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getMyHistoric(idUser: String) : Flow<Response<List<HistoricService>>> = flow {
        emit(Response.loading(true))

        val response = firestore
            .collection(QUEUE_COLLECTION)
            .get()
            .await()

        val queues = response.documents.map {
            QueueResponse().toQueueResponse(it.data)
        }
        val historic = mutableListOf<HistoricService>()

        queues.forEach { queue ->
            queue.service.forEach { service ->
                if(service.userId == idUser){
                    historic.add(
                        HistoricService(
                            queueName = queue.name,
                            insertedDate = service.enrollmentTime.toDate().toStringDate(DateFormats.NORMAL_DATE_WITH_HOURS_FORMAT),
                            status = CallStatus.getTextByValue(service.status),
                            idUser = idUser
                        )
                    )
                }
            }
        }

        emit(Response.success(historic.toList()))
    }.catch {
        emit(Response.error(it.message ?: UNKNOWN_ERROR))
    }.flowOn(Dispatchers.IO)
}