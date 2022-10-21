package com.tcc.alif.view.ui.administrator.queue

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.AdministratorRepository
import com.tcc.alif.data.util.request
import com.tcc.alif.view.ui.BaseState
import com.tcc.alif.view.ui.administrator.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueueViewModel @Inject constructor(
    private val repository: AdministratorRepository
): ViewModel(){

    val state = MutableLiveData<BaseState>()
    fun handleIntent(intent : QueueIntent){
        when(intent){
            is QueueIntent.GetCalls -> getCallsBy(intent.idQueue)
        }
    }

    private fun getCallsBy(idQueue : String){
        viewModelScope.launch {
            repository.getCallsBy(idQueue = idQueue).request(
                blockToRun = this,
                onSuccess = { state.postValue(BaseState.Success(it)) },
                onLoading = { state.postValue(BaseState.Loading(it)) },
                onError = { state.postValue(BaseState.Error(it)) }
            )
        }
    }
}