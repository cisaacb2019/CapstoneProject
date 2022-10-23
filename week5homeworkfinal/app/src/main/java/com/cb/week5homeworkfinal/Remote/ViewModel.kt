package com.cb.week5homeworkfinal.Remote

import androidx.lifecycle.*
import com.cb.week5homeworkfinal.DataBase.PrefsStore.PrefsStore
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.ModelData.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class myViewModel @Inject constructor(private val newsRepo: NewsRepo, private val prefsStore: PrefsStore): ViewModel() {

    class Factory(
        private val newsRepo: NewsRepo, private val prefsStore: PrefsStore
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return myViewModel(newsRepo, prefsStore) as T
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

    val wifiEnabled = prefsStore.isInternetMode().asLiveData()
    fun toggleinternetMode(){
        viewModelScope.launch {
            prefsStore.toogleinternetMode()
        }
    }
    fun testMessage(message: String): String {
        return message
    }
    fun testPrint(message: String) {
        println(message)
    }
}