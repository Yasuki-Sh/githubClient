package com.example.githubclient.data.remote

import com.example.githubclient.data.model.GithubResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET

private const val baseUrl = "https://api.github.com/"
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
private val client = okhttp3.OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
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
    @GET("users/Yasuki-Sh/repos")
    suspend fun getRepos(): List<GithubResponse>
}

object GithubApi {
    val retrofitService : GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java) }
}