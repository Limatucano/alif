package com.tcc.alif.view.ui.administrator.configuration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tcc.alif.data.repository.ConfigurationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    private val repository: ConfigurationRepository
) : ViewModel(){

    val state = MutableLiveData<ConfigurationState>()

    fun handleIntent(intent: ConfigurationIntent){
        when(intent){
            is ConfigurationIntent.Exit -> signOut()
        }
    }

    private fun signOut(){
        repository.signOut()
        state.postValue(ConfigurationState.ExitedSuccessfully)
    }
}