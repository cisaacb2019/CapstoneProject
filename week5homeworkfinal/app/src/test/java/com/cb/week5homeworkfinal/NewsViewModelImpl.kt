package com.cb.week5homeworkfinal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.Fragments.App.Companion.prefsStore
import com.cb.week5homeworkfinal.Remote.myViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class NewsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTest()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `When calling print message actually prints out the message`() {
        val mockRepo = spyk<NewsRepo>()

        every { mockRepo.isDataUsage() } answers {
            flow {
                val viewModel = myViewModel(mockRepo, prefsStore)

                Assert.assertEquals("This is a test", viewModel.testMessage("This is a test"))
                verify(exactly = 1) { mockRepo.isDataUsage() }
            }
        }
    }
        @Test
        fun `print message`() {
            val mockRepo = mockk<NewsRepo>()

            every { mockRepo.isDataUsage() } answers {
                flow { }
            }
            val viewModel = myViewModel(mockRepo, prefsStore)
            viewModel.testPrint("Test")
            verify(exactly = 1) { viewModel.testMessage("Test1") }
        }
    }