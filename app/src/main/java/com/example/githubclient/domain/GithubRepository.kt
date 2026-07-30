package com.example.githubclient.domain

import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.data.remote.GithubApi
import android.util.Base64
class GithubRepository {
    suspend fun getRepos(): Result<List<GithubResponse>> {
        return try {
            Result.success(GithubApi.retrofitService.getRepos("Yasuki-Sh"))// アプリの設定にてユーザーを指定できるよう実装予定
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getPrivateRepos(): Result<List<GithubResponse>> {
        return try {
            Result.success(GithubApi.retrofitService.getPrivateRepos())
        } catch (e: Exception) {
            Result.failure(e)
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