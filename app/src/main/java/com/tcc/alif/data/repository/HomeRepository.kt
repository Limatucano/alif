package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.HomeDataSource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeDataSource: HomeDataSource
) {

    fun getMyHistoric(idUser: String) = homeDataSource.getMyHistoric(idUser)
}