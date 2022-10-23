package com.tcc.alif.view.ui.administrator.queue

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.repository.AdministratorRepository
import com.tcc.alif.data.util.request
import com.tcc.alif.view.ui.BaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueueViewModel @Inject constructor(
    private val repository: AdministratorRepository
): ViewModel(){

    val state = MutableLiveData<QueueState>()
    fun handleIntent(intent : QueueIntent){
        when(intent){
            is QueueIntent.GetCalls -> getCallsBy(intent.idQueue)
            is QueueIntent.UpdateCallStatus -> updateCallStatus(
                status = intent.status,
                idUser = intent.idUser,
                idQueue = intent.idQueue
            )
        }
    }

    private fun updateCallStatus(
        status: CallStatus,
        idUser: String,
        idQueue: String
    ){
        viewModelScope.launch {
            repository.updateCallStatus(
                status = status,
                idUser = idUser,
                idQueue = idQueue
            ).request(
                blockToRun = this,
                onSuccess = { state.postValue(QueueState.CallUpdated(it)) },
                onLoading = { state.postValue(QueueState.Loading(it)) },
                onError = { state.postValue(QueueState.Error(it)) }
            )
        }
    }

    private fun getCallsBy(idQueue : String){
        viewModelScope.launch {
            repository.getCallsBy(idQueue = idQueue).request(
                blockToRun = this,
                onSuccess = { state.postValue(QueueState.Success(it)) },
                onLoading = { state.postValue(QueueState.Loading(it)) },
                onError = { state.postValue(QueueState.Error(it)) }
            )
        }
    }
}