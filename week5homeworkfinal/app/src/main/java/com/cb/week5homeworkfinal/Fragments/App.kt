package com.cb.week5homeworkfinal.Fragments

import android.app.Application
import com.cb.week5homeworkfinal.Remote.RemoteApi
import com.cb.week5homeworkfinal.Remote.buildAPIService

class App : Application() {
    private lateinit var instance: App
    companion object {
        private val apiService by lazy { buildAPIService() }
        val remoteApi by lazy { RemoteApi(apiService) }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}