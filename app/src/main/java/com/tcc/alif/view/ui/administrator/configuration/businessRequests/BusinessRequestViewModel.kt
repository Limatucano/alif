package com.tcc.alif.view.ui.administrator.configuration.businessRequests

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.EmployeeRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessRequestViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {

    val state = MutableLiveData<BusinessRequestState>()

    fun handleIntent(intent: BusinessRequestIntent){
        when(intent){
            is BusinessRequestIntent.GetAllBusinessRequests -> getAllBusinessRequests(intent.idUser)
        }
    }

    private fun getAllBusinessRequests(idUser: String){
        viewModelScope.launch {
            repository.getMyBusinessRequests(idUser).request(
                blockToRun = this,
                onSuccess = { state.postValue(BusinessRequestState.MyBusinessRequests(it)) },
                onError = { state.postValue(BusinessRequestState.Error(it)) },
                onLoading = { state.postValue(BusinessRequestState.Loading(it)) }
            )
        }
    }
}