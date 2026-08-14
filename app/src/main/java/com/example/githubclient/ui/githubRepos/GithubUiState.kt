package com.example.githubclient.ui.githubRepos

import com.example.githubclient.data.model.GithubErrorResponse
import com.example.githubclient.data.model.GithubResponse

sealed class GithubUiState {
    data class Success(val repos: List<GithubResponse>) : GithubUiState()
    data class Error(val error: GithubErrorResponse) : GithubUiState()
    object Loading : GithubUiState()
}