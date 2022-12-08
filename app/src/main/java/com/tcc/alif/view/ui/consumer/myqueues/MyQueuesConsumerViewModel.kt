package com.tcc.alif.view.ui.consumer.myqueues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.ConsumerRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyQueuesConsumerViewModel @Inject constructor(
    private val repository: ConsumerRepository
) : ViewModel() {

    val state = MutableLiveData<MyQueuesConsumerState>()

    fun handleIntent(intent: MyQueuesConsumerIntent){
        when(intent){
            is MyQueuesConsumerIntent.GetMyQueues -> getMyQueues(idUser = intent.idUser)
        }
    }

    private fun getMyQueues(idUser: String){
        viewModelScope.launch {
            repository.getMyQueues(idUser).request(
                blockToRun = this,
                onError = {
                    state.postValue(MyQueuesConsumerState.Error(it))
                },
                onLoading = {
                    state.postValue(MyQueuesConsumerState.Loading(it))
                },
                onSuccess = {
                    state.postValue(MyQueuesConsumerState.MyQueues(it))
                }
            )
        }
    }
}