package com.yunuscagliyan.core.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.cast.CastModel
import com.yunuscagliyan.core.data.remote.model.crew.CrewModel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CastCrewResponse(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "cast") var cast: List<CastModel>? = null,
    @Json(name = "crew") var crew: List<CrewModel>? = null,
) : Parcelable
