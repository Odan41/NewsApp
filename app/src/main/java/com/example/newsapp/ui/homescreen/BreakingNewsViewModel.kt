package com.example.newsapp.ui.homescreen

import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BreakingNewsViewModel(
    private val repo : NewsRepository,
) : BaseViewModel() {

    private val _news = MutableStateFlow<List<ArticleResponse>>(emptyList())
    val news = _news.asStateFlow()

    init {
        fetchBreakingNews()
    }

    private fun fetchBreakingNews(){
        launch {
            val news = repo.fetchBreakingNews()
            _news.emit(news)
        }
    }
}