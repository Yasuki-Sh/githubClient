package com.example.githubclient.ui

sealed class GithubReadmeUiState {
    data class Success(val readme: String) : GithubReadmeUiState()
    object Error : GithubReadmeUiState()
    object Loading : GithubReadmeUiState()
}