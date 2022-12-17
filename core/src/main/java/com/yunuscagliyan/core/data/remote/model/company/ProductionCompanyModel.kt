package com.yunuscagliyan.core.data.remote.model.company

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductionCompanyModel(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "logo_path") var logoPath: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "origin_country") var originCountry: String? = null
) : Parcelable
