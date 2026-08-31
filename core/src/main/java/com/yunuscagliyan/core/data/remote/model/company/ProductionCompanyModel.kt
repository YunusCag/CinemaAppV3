package com.yunuscagliyan.core.data.remote.model.company

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductionCompanyModel(
    @param:Json(name = "id") var id: Int? = null,
    @param:Json(name = "logo_path") var logoPath: String? = null,
    @param:Json(name = "name") var name: String? = null,
    @param:Json(name = "origin_country") var originCountry: String? = null
) : Parcelable
