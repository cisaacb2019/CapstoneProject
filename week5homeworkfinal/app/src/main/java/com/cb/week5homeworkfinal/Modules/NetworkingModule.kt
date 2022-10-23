package com.cb.week5homeworkfinal.Modules

import android.content.Context
import android.net.ConnectivityManager
import com.cb.week5homeworkfinal.ModelData.Constants.Companion.baseurl
import com.cb.week5homeworkfinal.Remote.NewsService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Provides
    @Singleton
    fun buildClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
            .build()

    @Provides
    @Singleton
    fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(buildClient())
            .baseUrl(baseurl).addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
    }

    @Provides
    @Singleton
    fun buildApiService(): NewsService = buildRetrofit().create(NewsService::class.java)

    @Provides
    @Singleton
    fun providesConnectivityManager(@ApplicationContext context: Context) : ConnectivityManager {
        return context.getSystemService(ConnectivityManager::class.java)

    }

    @Provides
    @Singleton
    fun providesGson () : Gson {
        return Gson()
    }


}