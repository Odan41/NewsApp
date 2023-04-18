package com.example.newsapp.ui.search

import com.example.newsapp.data.models.response.NewsResponse
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel (
    private val searchedWord: String,
    private val repo: NewsRepository,
) : BaseViewModel() {

    private val _searchedNews = MutableStateFlow<NewsResponse?>(null)
    val searchedNews = _searchedNews.asStateFlow()

    init {
        fetchRocketDetail()
    }

    private fun fetchRocketDetail() {
        launch {
            repo.fetchSearchNews(searchedWord)
                .let { responce ->
                    _searchedNews.emit(responce)
                }
        }
    }
}