package com.tcc.alif.view.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.repository.SigninRepository
import com.tcc.alif.data.util.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signinRepository: SigninRepository
) : ViewModel(){

    var user = MutableLiveData<SigninState>()

    fun handleIntent(intent : SigninIntent){
        when(intent){
            is SigninIntent.Signin -> authUser(intent.signin)
        }
    }

    private fun authUser(signin : Signin){
        viewModelScope.launch(Dispatchers.IO) {
            signinRepository.signin(signin).request(
                onSuccess = { user.postValue(SigninState.SuccessSignin(it)) },
                onLoading = { user.postValue(SigninState.Loading(it)) },
                onError = {user.postValue(SigninState.Error(it))}
            )
        }
    }


}