package com.example.newsapp.data.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NewsResponse (


    @SerialName("status")
    val status: String,

    @SerialName("totalResults")
    val totalResults: Int,

    @SerialName("articles")
    val articles: List<Article>,
    ){

    @Serializable
    data class Article(
        @SerialName("author") val author:String,
        @SerialName("title") val title:String,
        @SerialName("description") val description:String,
        @SerialName("url") val url:String,
        @SerialName("urlToImage") val urlToImage:String,
        @SerialName("publishedAt") val publishedAt:String,
        @SerialName("content") val content:String,
    )
}