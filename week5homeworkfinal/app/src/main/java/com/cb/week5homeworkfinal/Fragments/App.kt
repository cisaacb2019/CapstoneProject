package com.cb.week5homeworkfinal.Fragments

import android.app.Application
import com.cb.week5homeworkfinal.DataBase.NewsArticleDatabase
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepoImpl
import com.cb.week5homeworkfinal.Remote.RemoteApi
import com.cb.week5homeworkfinal.Remote.buildAPIService
import com.google.gson.Gson

class App : Application() {
    companion object {
        private lateinit var instance: App
        private val database: NewsArticleDatabase by lazy {
            NewsArticleDatabase.buildDatabase(instance)
        }
        private val newsApiService by lazy {
            buildAPIService()
        }

        val newsRepo: NewsRepo by lazy {
            NewsRepoImpl(database.articleDao(), newsApiService)
        }

        val GSONLAZY by lazy {
            Gson()
        }
    }
        override fun onCreate() {
            super.onCreate()
            instance = this
        }

}