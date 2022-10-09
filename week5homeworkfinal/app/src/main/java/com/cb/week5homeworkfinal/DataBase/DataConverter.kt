package com.cb.week5homeworkfinal.DataBase

import androidx.room.TypeConverter
import com.cb.week5homeworkfinal.Fragments.App
import com.cb.week5homeworkfinal.Source
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromSources(value: Source): String = App.GSONLAZY.toJson(value)
    @TypeConverter
    fun toSources(json: String): Source {
        val listType = object : TypeToken<Source>() {}.type
        return App.GSONLAZY.fromJson(json, listType)
    }
}