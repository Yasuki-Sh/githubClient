package com.example.githubclient.domain

import com.example.githubclient.BuildConfig
import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.data.remote.GithubApi

class GithubRepository {
    private val accessToken = BuildConfig.accessToken

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
}