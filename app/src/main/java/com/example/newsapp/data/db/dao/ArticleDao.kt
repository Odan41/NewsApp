package com.example.newsapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.data.models.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}