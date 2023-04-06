package com.example.newsapp.ui.detail

import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticleDetailViewModel(
    private val article: ArticleResponse,
) : BaseViewModel() {

    private val _articleDetail = MutableStateFlow<ArticleResponse?>(null)
    val articleDetail = _articleDetail.asStateFlow()

    init {
        fetchRocketDetail()
    }

    private fun fetchRocketDetail() {
        launch {
            val article = article
            _articleDetail.emit(article)
        }
    }
}