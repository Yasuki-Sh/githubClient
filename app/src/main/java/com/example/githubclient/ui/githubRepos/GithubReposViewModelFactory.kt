package com.example.githubclient.ui.githubRepos

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.data.local.GithubCredentialDataStore
import com.example.githubclient.domain.GithubRepository

class GithubReposViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val githubCredentialDataStore = GithubCredentialDataStore.getInstance(context)
        val repository = GithubRepository(githubCredentialDataStore)
        return GithubReposViewModel(repository) as T
    }
}