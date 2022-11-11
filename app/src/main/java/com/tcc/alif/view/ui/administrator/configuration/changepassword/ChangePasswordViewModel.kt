package com.tcc.alif.view.ui.administrator.configuration.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.repository.ConfigurationRepository
import com.tcc.alif.data.util.Constants.PASSWORD_ERROR
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
            is ChangePasswordIntent.ValidatePassword -> validPassword(
                validatePassword = intent.validatePassword,
                currentPassword = intent.currentPassword,
                newPassword = intent.newPassword,
                confirmPassword = intent.confirmPassword
            )
        }
    }

    private fun validPassword(
        validatePassword: String,
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ){
        when{
            currentPassword == newPassword ||
            newPassword != confirmPassword ||
            currentPassword != validatePassword -> {
                state.postValue(ChangePasswordState.Error(PASSWORD_ERROR))
            }
            else -> state.postValue(ChangePasswordState.PasswordValidated(
                newPassword = newPassword
            ))
        }
    }

    private fun updatePassword(newPassword: String){
        viewModelScope.launch {
            repository.updatePassword(newPassword = newPassword).request(
                blockToRun = this,
                onSuccess = { state.postValue(
                    ChangePasswordState.PasswordUpdated(
                        message = it,
                        password = newPassword
                    )
                ) },
                onLoading = { state.postValue(ChangePasswordState.Loading(it)) },
                onError = { state.postValue(ChangePasswordState.Error(it)) }
            )
        }
    }
}