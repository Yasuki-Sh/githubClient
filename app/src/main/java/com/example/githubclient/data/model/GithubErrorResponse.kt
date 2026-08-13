package com.example.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GithubErrorResponse (
    val status: String,
    val message: String,
    @SerialName("documentation_url") val documentationUrl: String
)

class GithubApiException(
    val errorResponse: GithubErrorResponse
) : Exception(errorResponse.message)