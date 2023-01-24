package com.yunuscagliyan.core.data.remote.model.video

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieVideoModel(
    @Json(name = "name") val name: String? = null,
    @Json(name = "key") val key: String? = null,
    @Json(name = "site") val site: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "official") val official: Boolean? = null,
    @Json(name = "published_at") val publishedAt: String? = null,
    @Json(name = "id") val id: String? = null,
) : Parcelable
