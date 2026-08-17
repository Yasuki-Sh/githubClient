package com.example.githubclient.data.remote

import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.data.model.ReadmeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("users/{owner}/repos")
    suspend fun getRepos(@Path("owner") owner: String): Response<List<GithubResponse>>

    @GET("user/repos")
    suspend fun getPrivateRepos(): Response<List<GithubResponse>>

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): ReadmeResponse
}