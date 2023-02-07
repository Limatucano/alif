package com.tcc.alif.view.ui.consumer.queuedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.CallStatus
import com.tcc.alif.data.model.Service
import com.tcc.alif.data.repository.ConsumerRepository
import com.tcc.alif.data.util.ValidateUtil.generateUUID
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueueConsumerViewModel @Inject constructor(
    private val repository: ConsumerRepository
): ViewModel(){

    val state = MutableLiveData<QueueConsumerState>()

    fun handleIntent(intent: QueueConsumerIntent){
        when(intent){
            is QueueConsumerIntent.CheckIfUserIsSubscribed -> {
                checkIfUserIsSubscribed(
                    idUser = intent.idUser,
                    idQueue = intent.idQueue
                )
            }
            is QueueConsumerIntent.CancelSubscription -> {
                cancelSubscription(
                    idQueue = intent.idQueue,
                    service = intent.service
                )
            }
            is QueueConsumerIntent.Subscribe -> {
                subscribe(
                    idQueue = intent.idQueue,
                    service = intent.service
                )
            }
        }
    }

    private fun subscribe(
        idQueue: String,
        service: Service
    ){
        viewModelScope.launch {
            repository.subscribe(
                idQueue = idQueue,
                service = service.copy(idService = generateUUID())
            ).request(
                blockToRun = this,
                onError = {
                    state.postValue(QueueConsumerState.Error(it))
                },
                onLoading = {
                    state.postValue(QueueConsumerState.Loading(it))
                },
                onSuccess = {
                    state.postValue(QueueConsumerState.Subscribed(it))
                }
            )
        }
    }

    private fun cancelSubscription(
        idQueue: String,
        service: Service
    ){
        viewModelScope.launch {
            repository.cancelSubscription(
                idQueue = idQueue,
                service = service
            ).request(
                blockToRun = this,
                onError = {
                    state.postValue(QueueConsumerState.Error(it))
                },
                onLoading = {
                    state.postValue(QueueConsumerState.Loading(it))
                },
                onSuccess = {
                    state.postValue(QueueConsumerState.CanceledSubscription(it))
                }
            )
        }
    }

    private fun checkIfUserIsSubscribed(
        idUser: String,
        idQueue: String
    ){
        viewModelScope.launch {
            repository.getMyQueues(idUser).request(
                blockToRun = this,
                onError = {
                    state.postValue(QueueConsumerState.Error(it))
                },
                onLoading = {
                    state.postValue(QueueConsumerState.Loading(it))
                },
                onSuccess = { myQueues ->
                    val queue = myQueues.filter {
                        it.status == CallStatus.IN_HOLD || it.status == CallStatus.IN_PROGRESS
                    }.firstOrNull {
                        it.idQueue == idQueue
                    }
                    state.postValue(
                        QueueConsumerState.AlreadySubscribed(
                            isSubscribed = (myQueues.isNotEmpty() && queue != null),
                            idService = queue?.idService,
                            response = queue
                        )
                    )
                }
            )
        }
    }
}