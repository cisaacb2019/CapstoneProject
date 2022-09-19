package com.cb.week5homeworkfinal.ModelData

import android.os.Parcelable
import com.cb.week5homeworkfinal.Source
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

//data class for article values nulled types that could be empty based off of api documentation
@Parcelize
data class Article (@Json(name = "source") val source: Source?, @Json(name = "author")val author:
String? = null, @Json(name = "title")val title: String? = null, @Json(name = "description")val description: String? = null,
                    @Json(name = "url")val url: String? = null, @Json(name = "urlToImage")val urlToImage: String? = null, @Json(name = "publishedAt")val publishedAt: String? = null,
                    @Json(name = "content")val content: String? = null,): Parcelable
