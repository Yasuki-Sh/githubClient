package com.example.githubclient.ui.githubRepoDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.data.local.GithubCredentialDataStore
import com.example.githubclient.domain.GithubRepository

class GithubRepoDetailViewModelFactory(
    private val context: Context,
    private val owner: String,
    private val repo: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val githubCredentialDataStore = GithubCredentialDataStore.getInstance(context)
        val repository = GithubRepository(githubCredentialDataStore)
        @Suppress("UNCHECKED_CAST")
        return GithubRepoDetailViewModel(owner, repo, repository) as T
    }
}