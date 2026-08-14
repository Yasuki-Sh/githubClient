package com.example.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubErrorResponse (
    val status: String? = null,
    val message: String,
    @SerialName("documentation_url") val documentationUrl: String
)

class GithubApiException(
    val errorResponse: GithubErrorResponse
) : Exception(errorResponse.message)