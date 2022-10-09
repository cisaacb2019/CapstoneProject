package com.cb.week5homeworkfinal
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
//class for source data
@Parcelize
@Entity(tableName = "sources")
data class Source (
    @Json(name = "id") val id: String?,
    @PrimaryKey
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "category") val category: Category? = null,
    @Json(name = "language") val language: Language? = null,
    @Json(name = "country") val country: Country? = null,
) : Parcelable

enum class Category {
    BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY,
}
enum class Language {
    AR,DE,EN,ES,FR,HE,IT,NL,NO,PR,RU,SV,UD,ZH,
}
enum class Country {
    AE,AR,AT,AU,BE,BG,BR,CA,CH,CN,CO,CU,CZ,DE,EG,FR,GB,GR,HK,HU,ID,IE,IL,IN,IT,JP,KR,LT,LV,MA,MX,MY,
    NG,NL,NO,NZ,PH,PL,PT,RO,RS,RU,SA,SE,SG,SI,SK,TH,TR,TW,UA,US,VE,ZA,
}