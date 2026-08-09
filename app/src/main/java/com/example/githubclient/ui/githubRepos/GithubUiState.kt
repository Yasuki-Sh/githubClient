package com.example.githubclient.ui.githubRepos

import com.example.githubclient.data.model.GithubResponse

sealed class GithubUiState {
    data class Success(val repos: List<GithubResponse>) : GithubUiState()
    object Error : GithubUiState()
    object Loading : GithubUiState()
}