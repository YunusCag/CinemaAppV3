package com.yunuscagliyan.core.data.remote.model.genre

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GenreModel(
    @param:Json(name = "id") val id: Int? = null,
    @param:Json(name = "name") val name: String? = null
) : Parcelable
