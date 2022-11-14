package com.tcc.alif.view.ui.administrator.configuration.mycategories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.CategoryResponse
import com.tcc.alif.data.repository.ConfigurationRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCategoriesViewModel @Inject constructor(
    private val repository: ConfigurationRepository
) : ViewModel() {

    val state = MutableLiveData<MyCategoriesState>()

    fun handleIntent(intent: MyCategoriesIntent){
        when(intent){
            is MyCategoriesIntent.GetAllCategories -> getAllCategories(intent.idCompany)
            is MyCategoriesIntent.DeleteCategory -> deleteCategory(intent.category)
            is MyCategoriesIntent.EditCategory -> editCategory(intent.category)
            is MyCategoriesIntent.InsertCategory -> insertCategory(intent.category)
        }
    }

    private fun getAllCategories(idCompany: String){
        viewModelScope.launch {
            repository.getAllCategories(idCompany).request(
                blockToRun = this,
                onError = { state.postValue(MyCategoriesState.Error(it)) },
                onLoading = { state.postValue(MyCategoriesState.Loading(it)) },
                onSuccess = { state.postValue(MyCategoriesState.AllCategories(it))}
            )
        }
    }

    private fun deleteCategory(category: CategoryResponse){
        viewModelScope.launch {
            repository.deleteCategory(category.uid).request(
                blockToRun = this,
                onError = { state.postValue(MyCategoriesState.Error(it)) },
                onLoading = { state.postValue(MyCategoriesState.Loading(it)) },
                onSuccess = { state.postValue(MyCategoriesState.CategoryDeleted(it)) }
            )
        }
    }

    private fun editCategory(category: CategoryResponse){
        viewModelScope.launch {
            repository.editCategory(category).request(
                blockToRun = this,
                onError = { state.postValue(MyCategoriesState.Error(it)) },
                onLoading = { state.postValue(MyCategoriesState.Loading(it)) },
                onSuccess = { state.postValue(MyCategoriesState.CategoryDeleted(it)) }
            )
        }
    }

    private fun insertCategory(category: CategoryResponse){
        viewModelScope.launch {
            repository.insertCategory(category).request(
                blockToRun = this,
                onError = { state.postValue(MyCategoriesState.Error(it)) },
                onLoading = { state.postValue(MyCategoriesState.Loading(it)) },
                onSuccess = { state.postValue(MyCategoriesState.CategoryInserted(it)) }
            )
        }
    }
}