package com.yunuscagliyan.core.data.remote.model.video

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieVideoModel(
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "key") val key: String? = null,
    @param:Json(name = "site") val site: String? = null,
    @param:Json(name = "type") val type: String? = null,
    @param:Json(name = "official") val official: Boolean? = null,
    @param:Json(name = "published_at") val publishedAt: String? = null,
    @param:Json(name = "id") val id: String? = null,
) : Parcelable
