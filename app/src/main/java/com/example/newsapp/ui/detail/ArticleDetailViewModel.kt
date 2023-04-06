package com.example.newsapp.ui.detail

import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticleDetailViewModel(
    private val articleName: String,
    private val repo: NewsRepository,
) : BaseViewModel() {

    private val _articleDetail = MutableStateFlow<ArticleResponse?>(null)
    val articleDetail = _articleDetail.asStateFlow()

    init {
        fetchRocketDetail()
    }

    private fun fetchRocketDetail() {
        launch {
            repo.fetchArticle(articleName)
                .let { detail ->
                    _articleDetail.emit(detail)
                }
        }
    }
}