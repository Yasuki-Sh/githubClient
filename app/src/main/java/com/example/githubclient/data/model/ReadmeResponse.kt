package com.example.githubclient.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReadmeResponse (
    val content: String, // Base64エンコードされたREADMEのコンテンツ
    val encoding: String
)