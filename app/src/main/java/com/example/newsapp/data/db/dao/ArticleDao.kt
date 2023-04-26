package com.example.newsapp.data.db.dao

import androidx.room.*
import com.example.newsapp.data.db.entities.ArticleEntity
import com.example.newsapp.data.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(article: ArticleEntity): Long

    @Query("SELECT * FROM ArticleEntity")
    fun selectAll(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM ArticleEntity WHERE title = :title")
    suspend fun deleteArticle(title:String)
}