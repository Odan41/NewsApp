package com.example.newsapp.data.api

import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("apiKey")
        apiKey: String = API_KEY
    ): List<ArticleResponse>

    @GET("everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): List<ArticleResponse>

    @GET("everything")
    suspend fun fetchArticle(
        @Query("q")
        articleName: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): ArticleResponse?
}