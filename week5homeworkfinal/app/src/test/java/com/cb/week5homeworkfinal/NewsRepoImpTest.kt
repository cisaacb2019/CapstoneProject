package com.cb.week5homeworkfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cb.week5homeworkfinal.DataBase.NewsDao
import com.cb.week5homeworkfinal.DataBase.PrefsStore.PrefsStore
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepoImpl
import com.cb.week5homeworkfinal.DataBase.SourceDao
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.Remote.NetworkStatusChecker
import com.cb.week5homeworkfinal.Remote.NewsService
import com.cb.week5homeworkfinal.Remote.RemoteApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsRepositoryImpTest {
    @MockK
    lateinit var mockArticleDao: NewsDao
    @MockK
    lateinit var mockNewsService:NewsService
    lateinit var testexeption: java.lang.Exception
    private lateinit var newsRepository: NewsRepoImpl
    private val dummyArticle = Article(
        Source(id = "Test" , name = "CNN"),
        "Test",
        "Test",
        "TestArticleDes",
        "www.tests.com",
        "www.tests.com",
        "CNN",
        "ShowThisTest",
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        newsRepository = NewsRepoImpl(
            mockArticleDao,
            mockNewsService
        )
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `When articles are searched through title return list of matched articles`() {

        coEvery { mockArticleDao.searchNews(any()) } answers {
            listOf(dummyArticle)
        }

        runBlocking {
            val articles = newsRepository.searchNews("test")

            Assert.assertEquals(1, articles.size)
        }
    }
    @Test
    fun `when articles are fetched ready with articles is returned`() {

        val flowArticleState = newsRepository.getNewsArticles()

        coEvery { mockArticleDao.getArticles() } returns listOf(dummyArticle)

        runBlocking {
            assertEquals(com.cb.week5homeworkfinal.ModelData.Result.Success(listOf(dummyArticle)), flowArticleState.first())
        }

    }
    @Test
    fun `When articles are searched through no articles are matched return empty`() {
        coEvery { mockArticleDao.searchNews(any()) } answers {
            listOf()
        }
        runBlocking {
            val articles = newsRepository.searchNews("US")
            Assert.assertEquals(0, articles.size)
        }
    }
    @Test
    fun `when articles are fetched partial articles is returned`() {

        val flowArticleState = newsRepository.getNewsArticles()

        coEvery { mockArticleDao.getArticles() } returns listOf(dummyArticle)

        runBlocking {
            assertEquals(com.cb.week5homeworkfinal.ModelData.Result.Failure(throw Exception("TEST")),
                flowArticleState.first())
        }
    }
}
