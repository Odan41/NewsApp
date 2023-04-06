package com.example.newsapp.data.db.dao

import androidx.room.*
import com.example.newsapp.data.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(article: Article): Long

    //@Query("SELECT * FROM articles")
    //fun getAllArticles(): Flow<List<Article>>

    //@Delete
    //suspend fun deleteArticle(article: Article)
}