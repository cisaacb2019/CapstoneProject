package com.cb.week5homeworkfinal.DataBase.Repo

import android.util.Log
import com.cb.week5homeworkfinal.Country
import com.cb.week5homeworkfinal.DataBase.NewsDao
import com.cb.week5homeworkfinal.DataBase.PrefsStore.PrefsStore
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.Constants.Companion.basekey
import com.cb.week5homeworkfinal.Remote.NetworkStatusChecker
import com.cb.week5homeworkfinal.Remote.NewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(
    private val articleDao: NewsDao,
    private val newsApiService: NewsService,
    private val networkStatusChecker: NetworkStatusChecker,
    private val prefs: PrefsStore
) : NewsRepo {

    override fun getNewsArticles(): Flow<com.cb.week5homeworkfinal.ModelData.Result<List<Article>>> {
        return flow {
            val newsArticlesFromLocalDb = articleDao.getArticles()

            emit(com.cb.week5homeworkfinal.ModelData.Result.Success(newsArticlesFromLocalDb))

            try {
                if (networkStatusChecker.hasInternetConnection()){
                val newsArticlesFromNetwork = newsApiService
                    .getNews(basekey, Country.US)
                    .articles

                emit(com.cb.week5homeworkfinal.ModelData.Result.Success(newsArticlesFromNetwork))

                if (newsArticlesFromNetwork.isNotEmpty()) {
                    articleDao.addArticles(newsArticlesFromNetwork)
                }
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
    override fun isDataUsage(): Flow<Boolean> = prefs.isInternetMode()
}