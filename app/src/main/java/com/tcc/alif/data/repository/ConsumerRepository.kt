package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.ConsumerDataSource
import com.tcc.alif.data.model.Service
import javax.inject.Inject

class ConsumerRepository @Inject constructor(
    private val consumerDataSource: ConsumerDataSource
) {

    fun getMyQueues(idUser: String) = consumerDataSource.getMyQueues(idUser)

    fun cancelSubscription(
        idQueue: String,
        service: Service
    ) = consumerDataSource.cancelSubscription(
        idQueue = idQueue,
        service = service
    )

    fun searchQueues(
        filter: String,
        byQrCode: Boolean
    ) = consumerDataSource.searchQueues(
        filter = filter,
        byQrCode = byQrCode
    )
}