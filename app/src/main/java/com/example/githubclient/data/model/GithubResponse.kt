package com.example.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubResponse (
    val name: String,
    val private: Boolean,
    val owner: Owner,
    @SerialName("html_url") val htmlUrl: String,
    val description: String?
)
@Serializable
data class Owner (
    val login: String,
)