package com.example.newsapp.data.repositories

import com.example.newsapp.data.api.NewsService

class NewsRepository(
    private val api: NewsService,
) {
    suspend fun fetchBreakingNews() = api.getBreakingNews()

    suspend fun  fetchSearchNews(searchWord: String) =
        api.searchForNews(searchWord)
}