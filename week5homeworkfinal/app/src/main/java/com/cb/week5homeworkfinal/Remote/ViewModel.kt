package com.cb.week5homeworkfinal.Remote

import androidx.lifecycle.*
import com.cb.week5homeworkfinal.Country
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.Constants.Companion.basekey
import com.cb.week5homeworkfinal.ModelData.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.cb.week5homeworkfinal.ModelData.Result
import kotlinx.coroutines.withContext

class myViewModel(private val newsRepo: NewsRepo): ViewModel() {

    class Factory(
        private val newsRepo: NewsRepo,
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return myViewModel(newsRepo) as T
        }
    }

    val articles: LiveData<com.cb.week5homeworkfinal.ModelData.Result<List<Article>>> =
        newsRepo.getNewsArticles().asLiveData()
}