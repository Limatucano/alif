package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.AdministratorDataSource
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.NotificationRequest
import com.tcc.alif.data.model.QueueRequest
import javax.inject.Inject

class AdministratorRepository @Inject constructor(
    private val administratorDataSource: AdministratorDataSource
) {

    suspend fun sendPushNotification(notificationRequest: NotificationRequest) =
        administratorDataSource.sendPushNotification(notificationRequest)

    fun updateCallStatus(
        status: CallStatus,
        idQueue: String,
        idEmployee: String,
        idUser: String
    ) = administratorDataSource.updateCallStatus(
        status = status,
        idQueue = idQueue,
        idEmployee = idEmployee,
        idUser = idUser
    )

    fun getCallsBy(idQueue: String) = administratorDataSource.getCallsByQueue(
        idQueue = idQueue
    )

    fun getQueuesByCompany(idCompany: String) = administratorDataSource.getQueuesByCompany(
        idCompany = idCompany
    )

    fun getQueuesFiltered(
        idCompany: String,
        filter: String
    ) = administratorDataSource.getQueuesByCompany(
        idCompany = idCompany,
        filter = filter
    )

    fun saveNewQueue(
        queue: QueueRequest
    ) = administratorDataSource.saveNewQueue(queue)

    fun updateQueue(
        idQueue: String,
        queue: Map<String, Comparable<*>?>
    ) = administratorDataSource.updateQueue(
        idQueue = idQueue,
        queue = queue
    )
}