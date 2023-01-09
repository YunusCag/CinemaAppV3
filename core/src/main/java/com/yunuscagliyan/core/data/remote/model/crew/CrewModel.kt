package com.yunuscagliyan.core.data.remote.model.crew

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CrewModel(
    @Json(name = "adult") var adult: Boolean? = null,
    @Json(name = "gender") var gender: Int? = null,
    @Json(name = "id") var id: Int? = null,
    @Json(name = "known_for_department") var knownForDepartment: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "original_name") var originalName: String? = null,
    @Json(name = "popularity") var popularity: Double? = null,
    @Json(name = "profile_path") var profilePath: String? = null,
    @Json(name = "credit_id") var creditId: String? = null,
    @Json(name = "department") var department: String? = null,
    @Json(name = "job") var job: String? = null,
) : Parcelable
