package com.example.newsapp.data.db

import android.content.Context
import androidx.room.*
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.db.dao.ArticleDao

@Database(
    entities = [Article::class],
    version = 1
)
abstract class ArticleDatabase : RoomDatabase() {
    
    abstract fun getArticleDao():ArticleDao

    companion object {
        const val Version = 1
        const val Name = "NewsDatabase"
    }
}