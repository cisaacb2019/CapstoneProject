package com.cb.week5homeworkfinal.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cb.week5homeworkfinal.Source

@Dao
interface SourceDao {
    @Query("SELECT * FROM sources")
    suspend fun getSources(): List<Source?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSources(source: List<Source?>)
}