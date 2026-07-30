package com.example.githubclient.data.remote

import com.example.githubclient.BuildConfig.accessToken
import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.data.model.ReadmeResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val baseUrl = "https://api.github.com/"
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
private val client = okhttp3.OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor(AuthInterceptor(accessToken))//のちにアプリの設定項目に変更予定
    .build()

private val json = Json{
    ignoreUnknownKeys = true
}

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface GithubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): List<GithubResponse>
    @GET("user/repos")
    suspend fun getPrivateRepos(): List<GithubResponse>

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): ReadmeResponse
}

object GithubApi {
    val retrofitService : GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java) }
}