package com.cb.week5homeworkfinal.Modules

import com.cb.week5homeworkfinal.Remote.NetworkStatusChecker
import com.cb.week5homeworkfinal.Remote.NetworkStatusCheckerImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InternetStatusModule {
    @Binds
    @Singleton
    abstract fun bindNetworkChecker(networkStatusCheckerImpl: NetworkStatusCheckerImp) : NetworkStatusChecker

}