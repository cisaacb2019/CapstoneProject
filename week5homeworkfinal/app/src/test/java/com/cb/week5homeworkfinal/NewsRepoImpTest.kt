package com.cb.week5homeworkfinal

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cb.week5homeworkfinal.DataBase.NewsDao
import com.cb.week5homeworkfinal.DataBase.PrefsStore.PrefsStore
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepoImpl
import com.cb.week5homeworkfinal.DataBase.SourceDao
import com.cb.week5homeworkfinal.ModelData.Article
import com.cb.week5homeworkfinal.Remote.NetworkStatusChecker
import com.cb.week5homeworkfinal.Remote.NewsService
import com.cb.week5homeworkfinal.Remote.RemoteApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsRepositoryImpTest {
    @MockK
    lateinit var testexeption: java.lang.Exception

    private val mockNewsService = mockk<NewsService>()
    private val mockNewsDao = mockk<NewsDao>()
    private val mockPrefsStore = mockk<PrefsStore>()
    private val mockNetworkStatusChecker = mockk<NetworkStatusChecker>()

    private val ServiceTest = NewsRepoImpl(mockNewsDao,
        mockNewsService,
        mockNetworkStatusChecker,
        mockPrefsStore
        )



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
    fun setup() {
        mockkStatic(Log::class)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `When articles are searched through title return list of matched articles`() {

        coEvery { mockNewsDao.searchNews(any()) } answers {
            listOf(dummyArticle)
        }

        runBlocking {
            val articles = ServiceTest.searchNews("test")

            Assert.assertEquals(1, articles.size)
        }
    }
    @Test
    fun `when articles are fetched ready with articles is returned`() {

        val flowArticleState = ServiceTest.getNewsArticles()

        coEvery { mockNewsDao.getArticles() } returns listOf(dummyArticle)

        runBlocking {
            assertEquals(com.cb.week5homeworkfinal.ModelData.Result.Success(listOf(dummyArticle)), flowArticleState.first())
        }

    }
    @Test
    fun `When articles are searched through no articles are matched return empty`() {
        coEvery { ServiceTest.searchNews(any()) } answers {
            listOf()
        }
        runBlocking {
            val articles = ServiceTest.searchNews("US")
            Assert.assertEquals(0, articles.size)
        }
    }
    //todo rework according to feedback on assignment//
//    @Test
//    fun `when articles are fetched partial articles is returned`() {
//
//        val flowArticleState = ServiceTest.getNewsArticles()
//
//        coEvery { mockNewsDao.getArticles() } returns listOf(dummyArticle)
//
//        runBlocking {
//            assertEquals(com.cb.week5homeworkfinal.ModelData.Result.Failure(throw Exception("TEST")),
//                flowArticleState.first())
//        }
//    }
}
