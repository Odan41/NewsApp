package com.example.newsapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class ArticleEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
)