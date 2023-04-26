package com.example.newsapp.ui.detail

import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.data.models.response.NewsResponse
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticleDetailViewModel(
    private val articleName: String,
    private val isBreaking: Boolean,
    private val repo: NewsRepository,
) : BaseViewModel() {

    private val _articleDetail = MutableStateFlow<NewsResponse>(NewsResponse("", 0, emptyList()))
    val articleDetail = _articleDetail.asStateFlow()

    init {
        fetchRocketDetail()
    }

    private fun fetchRocketDetail() {
        launch {
            if(isBreaking){
                repo.fetchBreakingArticle(articleName)
                    .let { responce ->
                        _articleDetail.emit(responce)
                    }
            }
            else{
                repo.fetchArticle(articleName)
                    .let { responce ->
                        _articleDetail.emit(responce)
                    }
            }

        }
    }
}