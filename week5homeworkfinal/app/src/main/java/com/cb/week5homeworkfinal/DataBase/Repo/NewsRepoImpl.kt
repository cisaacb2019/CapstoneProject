package com.cb.week5homeworkfinal.DataBase.Repo

import android.util.Log
import com.cb.week5homeworkfinal.Country
import com.cb.week5homeworkfinal.DataBase.NewsDao
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.Constants.Companion.basekey
import com.cb.week5homeworkfinal.Remote.NewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepoImpl(
    private val articleDao: NewsDao,
    private val newsApiService: NewsService
) : NewsRepo {

    override fun getNewsArticles(): Flow<com.cb.week5homeworkfinal.ModelData.Result<List<Article>>> {
        return flow {
            val newsArticlesFromLocalDb = articleDao.getArticles()

            emit(com.cb.week5homeworkfinal.ModelData.Result.Success(newsArticlesFromLocalDb))

            try {
                val newsArticlesFromNetwork = newsApiService
                    .getNews(basekey, Country.US)
                    .articles

                emit(com.cb.week5homeworkfinal.ModelData.Result.Success(newsArticlesFromNetwork))

                if (newsArticlesFromNetwork.isNotEmpty()){
                    articleDao.addArticles(newsArticlesFromNetwork)
                }
            }catch (e: Exception){
                Log.e(TAG, e.toString())
            }
        }

    }
    override suspend fun searchNews(search: String): List<Article> {
        return articleDao.searchNews(search)
    }
    companion object {
        private const val TAG = "ArticleRepoImpl"
    }
}