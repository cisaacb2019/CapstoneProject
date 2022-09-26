package com.cb.week5homeworkfinal.Remote

import com.cb.week5homeworkfinal.Country
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("apiKey") token : String,
        @Query("country") country: Country
    ) : NewsResponse
}
