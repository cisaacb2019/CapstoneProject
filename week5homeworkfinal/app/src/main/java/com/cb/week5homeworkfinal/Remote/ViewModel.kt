package com.cb.week5homeworkfinal.Remote

import androidx.lifecycle.*
import com.cb.week5homeworkfinal.Country
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.ModelData.Constants.Companion.basekey
import com.cb.week5homeworkfinal.ModelData.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.cb.week5homeworkfinal.ModelData.Result
import kotlinx.coroutines.withContext

class myViewModel(private val newsService: NewsService): ViewModel() {

    class ModelFactory(
        private val newsService: NewsService,
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return myViewModel(newsService) as T
        }
    }

    private val responsevalue = MutableLiveData< Result<NewsResponse>>()
    val articleLiveData: LiveData<Result<NewsResponse>> = responsevalue

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = newsService.getNews(basekey, Country.US)
                withContext(Dispatchers.Main){
                    responsevalue.value = Result.Success(response)
                }
            } catch (e: Exception){
                responsevalue.value = Result.Failure(e)
            }
        }

    }
}