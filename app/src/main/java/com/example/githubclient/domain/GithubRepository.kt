package com.example.githubclient.domain

import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.data.remote.GithubApi
import android.util.Base64
import com.example.githubclient.data.local.DataStore

class GithubRepository(private val dataStore: DataStore) {
    suspend fun getRepos(): Result<List<GithubResponse>> {
        val owner = dataStore.getCredentials().owner
        val token = dataStore.getCredentials().token
        return if(token != "") { // tokenがあるとき、プライベートリポジトリを取得する
            try {
                Result.success(GithubApi.retrofitService.getPrivateRepos())
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else { // tokenがないとき、パブリックリポジトリを取得する
            try {
                Result.success(GithubApi.retrofitService.getRepos(owner))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    suspend fun getReadme(owner: String, repo: String): Result<String> {
        return try {
            val response = GithubApi.retrofitService.getReadme(owner, repo)
            val decodedBytes = Base64.decode(response.content, Base64.DEFAULT)
            Result.success(String(decodedBytes, Charsets.UTF_8))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}