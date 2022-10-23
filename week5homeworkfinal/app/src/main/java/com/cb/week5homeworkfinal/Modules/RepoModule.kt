package com.cb.week5homeworkfinal.Modules

import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepo
import com.cb.week5homeworkfinal.DataBase.Repo.NewsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindRepository(newsArticleRepoImpl: NewsRepoImpl) : NewsRepo

}