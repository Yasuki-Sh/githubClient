package com.example.githubclient.data.remote

import com.example.githubclient.data.local.GithubCredentialDataStore
import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.data.model.ReadmeResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val baseUrl = "https://api.github.com/"
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
private val json = Json{
    ignoreUnknownKeys = true
}

interface GithubApiService {
    @GET("users/{owner}/repos")
    suspend fun getRepos(@Path("owner") owner: String): List<GithubResponse>
    @GET("user/repos")
    suspend fun getPrivateRepos(): List<GithubResponse>

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): ReadmeResponse
}

object GithubApi {
    private lateinit var retrofitService_: GithubApiService

    fun initialize(dataStore: GithubCredentialDataStore) {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(dataStore))
            .build()

        retrofitService_ = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GithubApiService::class.java)
    }

    val retrofitService get() = retrofitService_
}