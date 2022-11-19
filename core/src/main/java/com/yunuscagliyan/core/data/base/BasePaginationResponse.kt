package com.yunuscagliyan.core.data.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasePaginationResponse<T>(
    @field:Json(name = "page") val page: Int? = null,
    @field:Json(name = "results") val results: List<T>? = null,
    @field:Json(name = "total_results") val totalResults: Int? = null
)