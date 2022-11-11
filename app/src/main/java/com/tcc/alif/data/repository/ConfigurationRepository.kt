package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.ConfigurationDataSource
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val configurationDataSource: ConfigurationDataSource
){

    fun updatePassword(
        newPassword: String
    ) = configurationDataSource.updatePassword(
        newPassword = newPassword
    )
}