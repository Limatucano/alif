package com.tcc.alif.data.repository

import com.tcc.alif.data.datasource.ConfigurationDataSource
import com.tcc.alif.data.model.CategoryResponse
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val configurationDataSource: ConfigurationDataSource
){

    fun updatePassword(
        newPassword: String
    ) = configurationDataSource.updatePassword(
        newPassword = newPassword
    )

    fun getAllCategories(
        idCompany: String
    ) = configurationDataSource.getAllCategories(
        idCompany = idCompany
    )

    fun deleteCategory(
        idCategory: String
    ) = configurationDataSource.deleteCategory(
        idCategory = idCategory
    )

    fun insertCategory(
        category: CategoryResponse
    ) = configurationDataSource.insertNewCategory(
        category = category
    )

    fun editCategory(
        category: CategoryResponse
    ) = configurationDataSource.editCategory(
        category = category
    )
}