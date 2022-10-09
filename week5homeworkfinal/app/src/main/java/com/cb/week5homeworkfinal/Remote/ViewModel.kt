package com.cb.week5homeworkfinal.Remote

import androidx.lifecycle.*
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.ModelData.Article
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect

class myViewModel(private val newsRepo: NewsRepo): ViewModel() {

    class Factory(
        private val newsRepo: NewsRepo,
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return myViewModel(newsRepo) as T
        }
    }

    init {
        viewModelScope.launch(IO) {
            newsRepo
                .getNewsArticles()
                .onEach { newArticles ->
                    responseValue.postValue(newArticles)
                }
                .collect()
        }
    }

    fun searchArticles(search: String){
        viewModelScope.launch(IO) {
            val filteredArticles = newsRepo.searchNews("%$search%")
            responseValue.postValue(com.cb.week5homeworkfinal.ModelData.Result.Success(filteredArticles))
        }
    }

    private val responseValue = MutableLiveData<com.cb.week5homeworkfinal.ModelData.Result<List<Article>>>()
    val articles: LiveData<com.cb.week5homeworkfinal.ModelData.Result<List<Article>>> = responseValue
}