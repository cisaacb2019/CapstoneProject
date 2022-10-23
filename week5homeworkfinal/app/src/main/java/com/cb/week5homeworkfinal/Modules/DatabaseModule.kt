package com.cb.week5homeworkfinal.Modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.cb.week5homeworkfinal.DataBase.NewsArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val STORE_NAME = "user_preferences"
const val DATABASE_NAME = "NewsArticles"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext app : Context
    ) = Room.databaseBuilder(
        app,
        NewsArticleDatabase::class.java,
        DATABASE_NAME
    ).build()
    @Singleton
    @Provides
    fun provideArticleDao(db: NewsArticleDatabase) = db.articleDao()

    @Singleton
    @Provides
    fun provideSourceDao(db: NewsArticleDatabase) = db.sourceDao()

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE)
    }
}