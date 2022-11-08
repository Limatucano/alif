package com.tcc.alif.view.ui.administrator.queues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.QueueRequest
import com.tcc.alif.data.model.QueueResponse
import com.tcc.alif.data.repository.AdministratorRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueuesViewModel @Inject constructor(
    private val repository : AdministratorRepository
) : ViewModel(){

    val state = MutableLiveData<QueuesState>()

    fun handleIntent(intent : QueuesIntent){
        when(intent){
            is QueuesIntent.GetQueuesBy -> {
                getQueuesBy(
                    idCompany = intent.idCompany,
                    filter = intent.filter
                )
            }
            is QueuesIntent.SaveNewQueue -> {
                saveNewQueue(intent.queue)
            }
        }
    }

    private fun saveNewQueue(queue: QueueRequest){
        viewModelScope.launch {
            repository.saveNewQueue(queue)
                .request(
                    blockToRun = this,
                    onError = { state.postValue(QueuesState.Error(it)) },
                    onLoading = { state.postValue(QueuesState.Loading(it)) },
                    onSuccess = { state.postValue(QueuesState.QueueSaved(it)) }
                )
        }
    }

    private fun getQueuesBy(
        idCompany : String,
        filter: String
    ){
        viewModelScope.launch {
            repository.getQueuesFiltered(
                idCompany = idCompany,
                filter = filter
            ).request(
                blockToRun = this,
                onError = { state.postValue(QueuesState.Error(it)) },
                onLoading = { state.postValue(QueuesState.Loading(it)) },
                onSuccess = { state.postValue(QueuesState.QueuesData(it)) }
            )
        }
    }
}