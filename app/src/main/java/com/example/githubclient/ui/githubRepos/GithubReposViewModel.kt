package com.example.githubclient.ui.githubRepos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclient.data.model.GithubApiException
import com.example.githubclient.data.model.GithubErrorResponse
import com.example.githubclient.domain.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GithubReposViewModel @Inject constructor(
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
                    val apiException = throwable as? GithubApiException
                    _uiState.value = if (apiException != null) {
                        GithubUiState.Error(apiException.errorResponse)
                    } else {
                        GithubUiState.Error(
                            GithubErrorResponse(
                                "Error",
                                "Offline",
                                ""
                            )
                        )
                    }
                }
        }
    }
}