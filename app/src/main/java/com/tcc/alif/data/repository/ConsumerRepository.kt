package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.ConsumerDataSource
import javax.inject.Inject

class ConsumerRepository @Inject constructor(
    private val consumerDataSource: ConsumerDataSource
) {

    fun getMyQueues(idUser: String) = consumerDataSource.getMyQueues(idUser)
}