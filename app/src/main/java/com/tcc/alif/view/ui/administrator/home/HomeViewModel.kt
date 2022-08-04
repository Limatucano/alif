package com.tcc.alif.view.ui.administrator.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.AdministratorRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository : AdministratorRepository
) : ViewModel() {

    val state = MutableLiveData<HomeState>()

    fun handleIntent(intent : HomeIntent){
        when(intent){
            is HomeIntent.getQueuesBy -> {
                getQueuesBy(intent.idCompany)
            }
        }
    }

    private fun getQueuesBy(idCompany : String){
        viewModelScope.request (
                blockToRun = { repository.getQueuesBy(idCompany)},
                onSuccess = { state.postValue(HomeState.Success(it)) },
                onLoading = { state.postValue(HomeState.Loading(it)) },
                onError = {state.postValue(HomeState.Error(it))}
            )
        }
}