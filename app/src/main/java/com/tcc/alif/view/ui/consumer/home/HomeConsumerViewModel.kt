package com.tcc.alif.view.ui.consumer.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.HomeRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeConsumerViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val state = MutableLiveData<HomeConsumerState>()

    fun handleIntent(intent: HomeConsumerIntent){
        when(intent){
            is HomeConsumerIntent.LoadHistoric -> loadMyHistoric(intent.idUser)
        }
    }

    private fun loadMyHistoric(idUser: String){
        viewModelScope.launch {
            homeRepository.getMyHistoric(idUser).request(
                blockToRun = this,
                onSuccess = { state.postValue(HomeConsumerState.Historic(it)) },
                onLoading = { state.postValue(HomeConsumerState.Loading(it)) },
                onError = { state.postValue(HomeConsumerState.Error(it)) }
            )
        }
    }
}