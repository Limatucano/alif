package com.tcc.alif.view.ui.administrator.configuration.mycategories

import com.tcc.alif.data.model.CategoryResponse

//TODO: Improve CRUD states
sealed class MyCategoriesState{

    data class Error(val message: String) : MyCategoriesState()
    data class Loading(val loading: Boolean) : MyCategoriesState()
    data class AllCategories(val categories: List<CategoryResponse>) : MyCategoriesState()
    data class CategoryDeleted(val message: String) : MyCategoriesState()
    data class CategoryInserted(val message: String) : MyCategoriesState()
    data class CategoryEdited(val message: String) : MyCategoriesState()
}
