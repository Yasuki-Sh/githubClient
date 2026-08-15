package com.example.githubclient.ui.githubRepoDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.domain.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubRepoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: GithubRepository
): ViewModel() {

    private val owner: String = savedStateHandle[GithubRepoDetailArgs.OWNER] ?: ""
    private val repo: String = savedStateHandle[GithubRepoDetailArgs.REPO_NAME] ?: ""
    private val _readmeUiState =
        MutableStateFlow<GithubRepoDetailUiState>(GithubRepoDetailUiState.Loading)
    val readmeUiState: StateFlow<GithubRepoDetailUiState> = _readmeUiState

    init {
        fetchReadme(owner, repo)
    }

    fun fetchReadme(owner: String, repo: String){
        viewModelScope.launch {
            repository.getReadme(owner, repo)
                .onSuccess { readme ->
                    _readmeUiState.value = GithubRepoDetailUiState.Success(readme)
                }
                .onFailure { throwable ->
                    _readmeUiState.value = GithubRepoDetailUiState.Error
                    Log.e("GithubRepoDetailViewModel", "Failed to fetch readme", throwable)
                }
        }
    }
}