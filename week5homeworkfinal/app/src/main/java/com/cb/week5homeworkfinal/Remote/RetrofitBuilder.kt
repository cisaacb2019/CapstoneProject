package com.cb.week5homeworkfinal.Remote

import com.cb.week5homeworkfinal.ModelData.Constants.Companion.baseurl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


fun BuildClient(): OkHttpClient =

    OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY

    })
    .build()

fun BuildRetroFit(): Retrofit {

    return Retrofit.Builder()
        .client(BuildClient())
        .baseUrl(baseurl).addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

}

fun buildAPIService(): NewsService = BuildRetroFit().create(NewsService::class.java)
