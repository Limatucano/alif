package com.tcc.alif.view.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.repository.SigninRepository
import com.tcc.alif.data.util.requestFirebase
import com.tcc.alif.data.util.zipFirebaseToOther
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signinRepository: SigninRepository
) : ViewModel(){

    var state = MutableLiveData<SigninState>()

    fun handleIntent(intent : SigninIntent){
        when(intent){
            is SigninIntent.Signin -> authUser(intent.signin)
        }
    }

    private fun authUser(signin : Signin){
        viewModelScope.launch(Dispatchers.IO){
            signinRepository.signin(signin).collect{ response ->
                response.requestFirebase(
                    blockToRun = this,
                    onSuccess = { success ->

                        state.postValue(SigninState.SuccessSignin(success.user?.uid))
                    },
                    onError = { error ->
                        state.postValue(SigninState.Error(error))
                    },
                    onLoading = { loading ->
                        state.postValue(SigninState.Loading(loading))
                    }
                )
            }
        }
    }
}