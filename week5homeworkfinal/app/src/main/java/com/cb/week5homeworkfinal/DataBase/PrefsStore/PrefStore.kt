package com.cb.week5homeworkfinal.DataBase.PrefsStore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun isInternetMode(): Flow<Boolean>

    suspend fun toogleinternetMode()
}