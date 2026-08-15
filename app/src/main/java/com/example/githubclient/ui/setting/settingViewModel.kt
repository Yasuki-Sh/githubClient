package com.example.githubclient.ui.setting

import androidx.lifecycle.ViewModel
import com.example.githubclient.data.local.GithubCredentialDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.githubclient.data.local.UserCredentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val dataStore: GithubCredentialDataStore
): ViewModel() {

    private val _credentials = MutableStateFlow(UserCredentials("", ""))
    val credentials: StateFlow<UserCredentials> = _credentials.asStateFlow()

    init {
        getCredentials()
    }
    fun getCredentials() {
        viewModelScope.launch {
            _credentials.value = dataStore.getCredentials()
        }
    }

    suspend fun saveCredentials(owner: String, token: String) {
        dataStore.saveCredentials(owner, token)
    }
}