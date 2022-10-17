package com.cb.week5homeworkfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cb.week5homeworkfinal.DataBase.NewsArticleDatabase
import com.cb.week5homeworkfinal.DataBase.NewsDao
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var newsDatabase: NewsArticleDatabase
    private lateinit var articleDao: NewsDao
    @Before
    fun initDatabase() {
        newsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsArticleDatabase::class.java
        ).build()
        articleDao = newsDatabase.articleDao()
    }
    @After
    fun closeDatabase() {
        newsDatabase.close()
    }
    @Test
    fun `getArticlesReturnsEmptyList`() {
        runBlocking {
            val articles = articleDao.getArticles()
            Assert.assertEquals(0, articles.size)
        }
    }
}