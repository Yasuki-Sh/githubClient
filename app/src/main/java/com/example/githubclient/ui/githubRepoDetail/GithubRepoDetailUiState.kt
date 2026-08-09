package com.example.githubclient.ui.githubRepoDetail

sealed class GithubRepoDetailUiState {
    data class Success(val readme: String) : GithubRepoDetailUiState()
    object Error : GithubRepoDetailUiState()
    object Loading : GithubRepoDetailUiState()
}