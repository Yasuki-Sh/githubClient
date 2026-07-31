package com.example.githubclient.ui

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
    private val repository: GithubRepository = GithubRepository()
): ViewModel() {

    private val _readmeUiState = MutableStateFlow<GithubReadmeUiState>(GithubReadmeUiState.Loading)
    val readmeUiState: StateFlow<GithubReadmeUiState> = _readmeUiState

    init {
        fetchReadme()
    }

    fun fetchReadme(){
        viewModelScope.launch {
            repository.getReadme(owner, repo)
                .onSuccess { readme ->
                    _readmeUiState.value = GithubReadmeUiState.Success(readme)
                }
                .onFailure { throwable ->
                    _readmeUiState.value = GithubReadmeUiState.Error
                    Log.e("GithubRepoDetailViewModel", "Error: Readme.md not found", throwable)
                }
        }
    }
}