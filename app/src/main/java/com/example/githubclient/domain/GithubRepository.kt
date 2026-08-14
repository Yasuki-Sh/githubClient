package com.example.githubclient.domain

import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.data.remote.GithubApi
import android.util.Base64
import android.util.Log
import com.example.githubclient.data.local.GithubCredentialDataStore
import com.example.githubclient.data.model.GithubApiException
import com.example.githubclient.data.model.GithubErrorResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class GithubRepository(private val githubCredentialDataStore: GithubCredentialDataStore) {
    suspend fun getRepos(): Result<List<GithubResponse>> {
        val owner = githubCredentialDataStore.getCredentials().owner
        val token = githubCredentialDataStore.getCredentials().token

        val response = try {
            if (token != "") {
                GithubApi.retrofitService.getPrivateRepos()
            } else if(owner != ""){
                GithubApi.retrofitService.getRepos(owner)
            } else {
                return Result.failure(GithubApiException(GithubErrorResponse("Notice", "Input credential", "")))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
        return if (response.isSuccessful) {
            response.body()?.let { Result.success(it) }
                ?: Result.failure(Exception(GithubApiException(GithubErrorResponse("Error", "Response is null", ""))))
        } else {
            val errorJson = response.errorBody()?.string()
            val apiError = errorJson?.let {
                try {
                    Json.decodeFromString<GithubErrorResponse>(it)
                } catch (e: SerializationException) {
                    null
                }
            }
            Log.e("GithubRepository", apiError?.documentationUrl ?: "documentation url not found")
            Result.failure(
                GithubApiException(
                    apiError?.copy(status = apiError.status ?: response.code().toString())
                        ?: GithubErrorResponse(
                        response.code().toString(),
                        "想定外のエラーが発生しました",
                        ""
                    )
                )
            )
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