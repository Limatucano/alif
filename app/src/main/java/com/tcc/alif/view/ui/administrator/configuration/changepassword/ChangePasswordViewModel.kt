package com.tcc.alif.view.ui.administrator.configuration.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.ConfigurationRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val repository: ConfigurationRepository
) : ViewModel() {

    val state = MutableLiveData<ChangePasswordState>()

    fun handleIntent(intent: ChangePasswordIntent){
        when(intent){
            is ChangePasswordIntent.UpdatePassword -> updatePassword(intent.newPassword)
        }
    }

    private fun updatePassword(newPassword: String){
        viewModelScope.launch {
            repository.updatePassword(newPassword = newPassword).request(
                blockToRun = this,
                onSuccess = { state.postValue(ChangePasswordState.PasswordUpdated(it)) },
                onLoading = { state.postValue(ChangePasswordState.Loading(it)) },
                onError = { state.postValue(ChangePasswordState.Error(it)) }
            )
        }
    }
}