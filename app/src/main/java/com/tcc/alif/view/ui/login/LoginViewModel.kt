package com.tcc.alif.view.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.model.SigninResponse
import com.tcc.alif.data.model.local.AccountType
import com.tcc.alif.data.repository.SigninRepository
import com.tcc.alif.data.util.Constants.USER_UNAUTHORIZED
import com.tcc.alif.data.util.UNKNOWN_ERROR
import com.tcc.alif.data.util.requestFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            state.postValue(SigninState.Loading(true))
            try {
                signinRepository.signin(signin)?.let {
                    getUserData(
                        uid = it.uid,
                        accountType = signin.type
                    )
                } ?: run {
                    state.postValue(SigninState.Error(UNKNOWN_ERROR))
                    state.postValue(SigninState.Loading(false))
                }
            } catch (e: FirebaseAuthException){
                state.postValue(SigninState.Error(UNKNOWN_ERROR))
                state.postValue(SigninState.Loading(false))
            }
        }
    }

    private fun getUserData(uid: String, accountType: AccountType) {
        signinRepository.getUserData(uid).requestFirebase(
            blockToRun = viewModelScope,
            onSuccess = {
                viewModelScope.launch {
                    val userData = SigninResponse().toSignResponse(
                        map = it.documents[0].data!!
                    )
                    val accessIsValid = when(accountType){
                        AccountType.ADMINISTRATOR -> userData.isAdministrator
                        AccountType.CONSUMER -> userData.isConsumer
                    } ?: false

                    if(accessIsValid){
                        state.postValue(
                            SigninState.Success(
                                user = userData
                            )
                        )
                    } else {
                        state.postValue(SigninState.Loading(false))
                        state.postValue(
                            SigninState.Error(
                                message = USER_UNAUTHORIZED
                            )
                        )
                    }
                }
            },
            onError = { state.postValue(SigninState.Error(it)) },
            onLoading = { state.postValue(SigninState.Loading(it))}
        )
    }
}