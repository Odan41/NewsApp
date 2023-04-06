package com.example.newsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Article (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
)