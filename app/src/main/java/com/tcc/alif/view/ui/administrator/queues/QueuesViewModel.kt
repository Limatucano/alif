package com.tcc.alif.view.ui.administrator.queues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.QueueRequest
import com.tcc.alif.data.model.QueueRequest.Companion.modelToMap
import com.tcc.alif.data.repository.AdministratorRepository
import com.tcc.alif.data.repository.ConfigurationRepository
import com.tcc.alif.data.repository.EmployeeRepository
import com.tcc.alif.data.util.request
import com.tcc.alif.view.ui.administrator.configuration.mycategories.MyCategoriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueuesViewModel @Inject constructor(
    private val repository : AdministratorRepository,
    private val configurationRepository: ConfigurationRepository,
    private val employeeRepository: EmployeeRepository
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
            is QueuesIntent.UpdateQueue -> {
                updateQueue(intent.queue)
            }
            is QueuesIntent.GetAllCategories -> {
                getAllCategories(intent.idCompany)
            }
            is QueuesIntent.GetMyEmployees -> {
                getMyEmployees(intent.idCompany)
            }
        }
    }

    private fun getMyEmployees(idCompany: String){
        viewModelScope.launch {
            employeeRepository.getMyEmployee(idCompany).request(
                blockToRun = this,
                onError = { state.postValue(QueuesState.Error(it)) },
                onLoading = { state.postValue(QueuesState.Loading(it)) },
                onSuccess = { state.postValue(QueuesState.MyEmployees(it)) }
            )
        }
    }

    private fun getAllCategories(idCompany: String){
        viewModelScope.launch {
            configurationRepository.getAllCategories(idCompany).request(
                blockToRun = this,
                onError = { state.postValue(QueuesState.Error(it)) },
                onLoading = { state.postValue(QueuesState.Loading(it)) },
                onSuccess = { state.postValue(QueuesState.AllCategories(it)) }
            )
        }
    }

    private fun updateQueue(queue: QueueRequest){
        viewModelScope.launch {
            repository.updateQueue(
                idQueue = queue.idQueue,
                queue = queue.modelToMap()
            )
                .request(
                    blockToRun = this,
                    onError = { state.postValue(QueuesState.Error(it)) },
                    onLoading = { state.postValue(QueuesState.Loading(it)) },
                    onSuccess = { state.postValue(QueuesState.QueueUpdated(it)) }
                )
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