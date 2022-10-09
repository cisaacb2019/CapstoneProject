package com.cb.week5homeworkfinal.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.Source


private const val DATABASE_VERSION = 1
@Database(entities = [Article::class, Source::class] , version = DATABASE_VERSION)
@TypeConverters(DataConverter::class)
abstract class NewsArticleDatabase : RoomDatabase() {
    companion object{
        private const val DATABASE_NAME = "NewsArticles"
        fun buildDatabase(context: Context): NewsArticleDatabase {
            return Room.databaseBuilder(
                context,
                NewsArticleDatabase::class.java,
                DATABASE_NAME,
            )
                .allowMainThreadQueries()
                .build()
        }
    }
    abstract fun articleDao(): NewsDao
    abstract fun sourceDao(): SourceDao
}