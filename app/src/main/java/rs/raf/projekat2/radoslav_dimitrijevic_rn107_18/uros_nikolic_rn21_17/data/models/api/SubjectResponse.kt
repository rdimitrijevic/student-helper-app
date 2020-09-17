package rs.raf.projekat2.radoslav_dimitrijevic_rn107_18.uros_nikolic_rn21_17.data.models.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubjectResponse(
    @Json(name = "predmet") val subjectName: String,
    @Json(name = "tip") val subjectType: String,
    @Json(name = "nastavnik") val professor: String,
    @Json(name = "ucionica") val classroom: String,
    @Json(name = "grupe") val groups: String,
    @Json(name = "dan") val day: String,
    @Json(name = "termin") val time: String
)