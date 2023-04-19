package com.example.newsapp.data.db

import android.content.Context
import androidx.room.*
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.db.dao.ArticleDao
import com.example.newsapp.data.db.entities.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = ArticleDatabase.Version
)
abstract class ArticleDatabase : RoomDatabase() {
    
    abstract fun articleDao():ArticleDao

    companion object {
        const val Version = 1
        const val Name = "NewsDatabase"
    }
}