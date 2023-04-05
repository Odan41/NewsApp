package com.example.newsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
class Article (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: com.example.umte_project_news.data.models.Source,
    val title: String,
    val url: String,
    val urlToImage: String
)