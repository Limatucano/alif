package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.AdministratorDataSource
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.Calls
import javax.inject.Inject

class AdministratorRepository @Inject constructor(
    private val administratorDataSource: AdministratorDataSource
) {

    fun updateCallStatus(
        status: CallStatus,
        idQueue: String,
        idUser: String
    ) = administratorDataSource.updateCallStatus(
        status = status,
        idQueue = idQueue,
        idUser = idUser
    )

    fun getCallsBy(idQueue: String) = administratorDataSource.getCallsByQueue(
        idQueue = idQueue
    )

    fun getQueuesByCompany(idCompany: String) = administratorDataSource.getQueuesByCompany(
        idCompany = idCompany
    )

}