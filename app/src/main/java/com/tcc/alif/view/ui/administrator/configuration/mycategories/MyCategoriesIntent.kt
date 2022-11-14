package com.tcc.alif.view.ui.administrator.configuration.mycategories

import com.tcc.alif.data.model.CategoryResponse

sealed class MyCategoriesIntent{
    data class GetAllCategories(val idCompany: String) : MyCategoriesIntent()
    data class InsertCategory(val category: CategoryResponse) : MyCategoriesIntent()
    data class EditCategory(val category: CategoryResponse) : MyCategoriesIntent()
    data class DeleteCategory(val category: CategoryResponse) : MyCategoriesIntent()
}
