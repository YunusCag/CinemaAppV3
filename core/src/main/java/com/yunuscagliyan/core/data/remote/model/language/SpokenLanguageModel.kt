package com.yunuscagliyan.core.data.remote.model.language

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SpokenLanguageModel(
    @Json(name = "name") var name: String? = null
): Parcelable
