package com.example.githubclient.data.remote

import com.example.githubclient.data.local.GithubCredentialDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(
    private val dataStore: GithubCredentialDataStore
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStore.getCredentials().token }
        val request = if(token.isNotEmpty()) {
            chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
    } else {
        chain.request() // トークンなしはヘッダを付けない
    }
    return chain.proceed(request)
    }
}