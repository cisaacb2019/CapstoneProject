package com.cb.week5homeworkfinal.Remote


import com.cb.week5homeworkfinal.ModelData.Constants.Companion.basekey
import com.cb.week5homeworkfinal.ModelData.NewsResponse
import com.cb.week5homeworkfinal.ModelData.Result

class RemoteApi (private val newsService: NewsService) {
    suspend fun getArticles()
            : Result<NewsResponse> = try {
        val articles = newsService.getNews(basekey)
        Result.Success(articles)
    }catch (e: Exception) {
        Result.Failure(e)
    }

}