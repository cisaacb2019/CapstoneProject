package com.cb.week5homeworkfinal.DataBase.Repo

import com.cb.week5homeworkfinal.ModelData.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepo {
    fun getNewsArticles() : Flow<com.cb.week5homeworkfinal.ModelData.Result<List<Article>>>
    suspend fun searchNews(search: String) : List<Article>
    fun isDataUsage(): Flow<Boolean>
}