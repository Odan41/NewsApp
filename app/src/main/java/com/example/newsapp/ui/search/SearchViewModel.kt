package com.example.newsapp.ui.search

import com.example.newsapp.data.models.response.NewsResponse
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel (
    private val repo: NewsRepository,
) : BaseViewModel() {

    private val _searchedText = MutableStateFlow("")
    val searchedText = _searchedText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchedNews = MutableStateFlow<NewsResponse?>(null)
    val searchedNews = _searchedNews.asStateFlow()

    init {
        onSearchTextChange("")
    }

    fun onSearchTextChange(text: String){
        _searchedText.value = text
        launch {
            repo.fetchSearchNews(text)
                .let { responce ->
                    _searchedNews.emit(responce)
                }
        }
    }
}