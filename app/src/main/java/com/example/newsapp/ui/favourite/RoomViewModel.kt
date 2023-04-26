package com.example.newsapp.ui.favourite

import com.example.newsapp.data.db.dao.ArticleDao
import com.example.newsapp.data.db.entities.ArticleEntity
import com.example.newsapp.data.models.response.NewsResponse
import com.example.newsapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.*

class RoomViewModel(
    private val articleDao: ArticleDao
): BaseViewModel() {

    val articles = articleDao.selectAll()
    private val _isDatabaseArticle = MutableStateFlow(false)
    val isDatabaseArticle = _isDatabaseArticle.asStateFlow()

    fun addArticle(article:NewsResponse.Article){
        launch {
            articleDao.insertOrUpdate(
                article = ArticleEntity(
                    author = article.author.orEmpty(),
                    content = article.content.orEmpty(),
                    description = article.description.orEmpty(),
                    publishedAt = article.publishedAt,
                    title = article.title,
                    url = article.url,
                    urlToImage = article.urlToImage.orEmpty()

                )
            )
        }

    }

    fun removeArticle(article:NewsResponse.Article){
        launch {
            articleDao.deleteArticle(article.title)
        }

    }

}