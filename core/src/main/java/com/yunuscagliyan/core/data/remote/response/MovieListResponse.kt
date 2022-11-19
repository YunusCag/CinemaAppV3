package com.yunuscagliyan.core.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieListResponse(
    @field:Json(name = "page") val page: Int? = null,
    @field:Json(name = "dates") val dates: MovieListDate? = null,
    @field:Json(name = "results") val results: List<MovieModel>? = null,
    @field:Json(name = "total_results") val totalResults: Int? = null
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieListDate(
    @field:Json(name = "maximum") val maximum: String? = null,
    @field:Json(name = "minimum") val minimum: String? = null,
) : Parcelable