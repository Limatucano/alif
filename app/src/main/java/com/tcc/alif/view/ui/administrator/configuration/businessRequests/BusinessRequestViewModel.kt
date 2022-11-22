package com.tcc.alif.view.ui.administrator.configuration.businessRequests

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.BusinessRequestsResponse
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
            is BusinessRequestIntent.UpdateBusinessRequest -> updateBusinessRequest(
                businessRequest = intent.businessRequest,
                newStatus = intent.newStatus
            )
        }
    }

    private fun updateBusinessRequest(
        businessRequest: BusinessRequestsResponse,
        newStatus: String
    ){
        viewModelScope.launch {
            repository.updateBusinessRequest(
                businessRequest = businessRequest,
                newStatus = newStatus
            ).request(
                blockToRun = this,
                onSuccess = { state.postValue(BusinessRequestState.BusinessUpdated(it)) },
                onError = { state.postValue(BusinessRequestState.Error(it)) },
                onLoading = { state.postValue(BusinessRequestState.Loading(it)) }
            )
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