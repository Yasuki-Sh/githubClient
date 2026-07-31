package com.example.githubclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GithubRepoDetailViewModelFactory(
    private val owner: String,
    private val repo: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GithubRepoDetailViewModel(owner, repo) as T
    }
}