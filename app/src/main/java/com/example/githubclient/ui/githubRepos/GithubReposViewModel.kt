package com.example.githubclient.ui.githubRepos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.domain.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GithubReposViewModel(
    private val repository: GithubRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<GithubUiState>(GithubUiState.Loading)
    val uiState: StateFlow<GithubUiState> = _uiState

    init {
        getRepos()
    }

    fun getRepos() {
        _uiState.value = GithubUiState.Loading
        viewModelScope.launch {
            repository.getRepos()
                .onSuccess { repos ->
                    _uiState.value = GithubUiState.Success(repos)
                }
                .onFailure { throwable ->
                    _uiState.value = GithubUiState.Error
                    Log.e("GithubReposViewModel", "Error: Failed to fetch repos", throwable)
                }
        }
    }
}