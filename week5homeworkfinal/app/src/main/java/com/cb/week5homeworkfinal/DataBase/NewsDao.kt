package com.cb.week5homeworkfinal.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cb.week5homeworkfinal.ModelData.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(article: List<Article>)
}