package com.cb.week5homeworkfinal.Remote

import androidx.lifecycle.*
import com.cb.week5homeworkfinal.Country
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.Constants.Companion.basekey
import com.cb.week5homeworkfinal.ModelData.NewsResponse
import kotlinx.coroutines.launch

class myViewModel(private val newsService: NewsService): ViewModel() {

    class ModelFactory(
        private val newsService: NewsService,
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return myViewModel(newsService) as T
        }
    }

    private val responsevalue = MutableLiveData<NewsResponse>()
    val base: LiveData<NewsResponse> = responsevalue


    init {
        viewModelScope.launch {
            try {
                val response = newsService.getNews(basekey,Country.US)
                responsevalue.value = response

            } catch (e: Exception){
                com.cb.week5homeworkfinal.ModelData.Result.Failure(e)
            }
        }

    }
}