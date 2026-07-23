package com.example.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubResponse (
    @SerialName("full_name") val fullName: String,
    val private: Boolean,
    @SerialName("html_url") val htmlUrl: String,
    val description: String?
)