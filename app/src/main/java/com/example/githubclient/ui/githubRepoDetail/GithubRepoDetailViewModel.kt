package com.example.githubclient.ui.githubRepoDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.domain.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GithubRepoDetailViewModel(
    private val owner: String,
    private val repo: String,
    private val repository: GithubRepository
): ViewModel() {

    private val _readmeUiState =
        MutableStateFlow<GithubRepoDetailUiState>(GithubRepoDetailUiState.Loading)
    val readmeUiState: StateFlow<GithubRepoDetailUiState> = _readmeUiState

    init {
        fetchReadme()
    }

    fun fetchReadme(){
        viewModelScope.launch {
            repository.getReadme(owner, repo)
                .onSuccess { readme ->
                    _readmeUiState.value = GithubRepoDetailUiState.Success(readme)
                }
                .onFailure { throwable ->
                    _readmeUiState.value = GithubRepoDetailUiState.Error
                    Log.e("GithubRepoDetailViewModel", "Error: Readme.md not found", throwable)
                }
        }
    }
}