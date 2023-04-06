package com.example.newsapp.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    val id: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String
)
