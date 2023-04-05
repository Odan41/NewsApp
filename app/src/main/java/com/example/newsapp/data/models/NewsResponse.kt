package com.example.newsapp.data.models

import com.example.newsapp.data.models.Article

class NewsResponse (
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)