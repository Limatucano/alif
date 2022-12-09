package com.tcc.alif.view.ui.consumer.queues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.ConsumerRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueuesConsumerViewModel @Inject constructor(
    private val repository: ConsumerRepository
) : ViewModel() {

    val state = MutableLiveData<QueuesConsumerState>()

    fun handleIntent(intent: QueuesConsumerIntent){
        when(intent){
            is QueuesConsumerIntent.SearchQueues -> { searchQueues(intent.filter) }
        }
    }

    private fun searchQueues(filter: String){
        viewModelScope.launch {
            repository.searchQueues(filter).request(
                blockToRun = this,
                onSuccess = { state.postValue(QueuesConsumerState.Queues(it)) },
                onLoading = { state.postValue(QueuesConsumerState.Loading(it)) },
                onError = { state.postValue(QueuesConsumerState.Error(it)) }
            )
        }
    }
}