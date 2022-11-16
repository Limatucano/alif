package com.tcc.alif.view.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.repository.ConfigurationRepository
import com.tcc.alif.data.util.request
import com.tcc.alif.view.ui.administrator.configuration.mycategories.MyCategoriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: ConfigurationRepository
) : ViewModel() {

    val state = MutableLiveData<UserProfileState>()

    fun handleIntent(intent: UserProfileIntent){
        when(intent){
            is UserProfileIntent.UpdateUserData -> { updateDataUser(intent.data) }
        }
    }

    private fun updateDataUser(userData: SigninResponse){
        viewModelScope.launch {
            repository.updateUserData(userData).request(
                blockToRun = this,
                onError = { state.postValue(UserProfileState.Error(it)) },
                onLoading = { state.postValue(UserProfileState.Loading(it)) },
                onSuccess = { state.postValue(UserProfileState.UserDataUpdated(it)) }
            )
        }
    }

}