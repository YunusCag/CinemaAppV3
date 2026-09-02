package com.yunuscagliyan.core.data.remote.model.cast

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CastModel(
    @param:Json(name = "adult") var adult: Boolean? = null,
    @param:Json(name = "gender") var gender: Int? = null,
    @param:Json(name = "id") var id: Int? = null,
    @param:Json(name = "known_for_department") var knownForDepartment: String? = null,
    @param:Json(name = "name") var name: String? = null,
    @param:Json(name = "original_name") var originalName: String? = null,
    @param:Json(name = "popularity") var popularity: Double? = null,
    @param:Json(name = "profile_path") var profilePath: String? = null,
    @param:Json(name = "cast_id") var castId: Int? = null,
    @param:Json(name = "character") var character: String? = null,
    @param:Json(name = "credit_id") var creditId: String? = null,
    @param:Json(name = "order") var order: Int? = null,
) : Parcelable
