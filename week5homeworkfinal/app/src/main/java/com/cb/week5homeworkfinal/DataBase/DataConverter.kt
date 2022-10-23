package com.cb.week5homeworkfinal.DataBase

import androidx.room.TypeConverter
import com.cb.week5homeworkfinal.Fragments.App
import com.cb.week5homeworkfinal.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    val gson = Gson()
    @TypeConverter
    fun fromSources(value: Source): String = gson.toJson(value)
    @TypeConverter
    fun toSources(json: String): Source {
        val listType = object : TypeToken<Source>() {}.type
        return gson.fromJson(json, listType)
    }
}