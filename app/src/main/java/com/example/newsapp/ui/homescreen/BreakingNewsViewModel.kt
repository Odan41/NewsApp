package com.example.newsapp.ui.homescreen

import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.data.models.response.NewsResponse
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.base.BaseViewModel
import com.example.newsapp.ui.base.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.androidx.viewmodel.scope.emptyState

class BreakingNewsViewModel(
    private val repo : NewsRepository,
) : BaseViewModel() {
    private val _news = MutableStateFlow<NewsResponse?>(null)
    val news = _news.asStateFlow()

    init {
        fetchBreakingNews()
    }

    private fun fetchBreakingNews(){
        launch {
            repo.fetchBreakingNews()
                .let { responce ->
                _news.emit(responce)
            }
        }
    }
}