package com.example.githubclient.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.domain.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GithubReposViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<GithubUiState>(GithubUiState.Loading)
    val uiState: StateFlow<GithubUiState> = _uiState

    private val repository = GithubRepository()

    fun fetchGithubRepos() { // accessTokenがない場合に、publicリポジトリを取得する
        viewModelScope.launch {
            repository.getRepos()
                .onSuccess { repos ->
                    _uiState.value = GithubUiState.Success(repos)
                }
                .onFailure { throwable ->
                    _uiState.value = GithubUiState.Error
                    Log.e("GithubReposViewModel", "Error: Failed to fetch public repos", throwable)
                }
        }
    }
    fun fetchGithubPrivateRepos() {
        viewModelScope.launch {
            repository.getPrivateRepos()
                .onSuccess { repos ->
                    _uiState.value = GithubUiState.Success(repos)
                    }
                .onFailure { throwable ->
                    _uiState.value = GithubUiState.Error
                    Log.e("GithubReposViewModel", "Error: Failed to fetch private repos", throwable)
                }
        }
    }
}