package com.yunuscagliyan.core.data.remote.model.movie


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieModel(
    @param:Json(name = "adult") val adult: Boolean? = null,
    @param:Json(name = "backdrop_path") val backdropPath: String? = null,
    @param:Json(name = "genre_ids") val genreIds: List<Int>? = null,
    @param:Json(name = "id") val id: Int? = null,
    @param:Json(name = "original_language") val originalLanguage: String? = null,
    @param:Json(name = "original_title") val originalTitle: String? = null,
    @param:Json(name = "overview") val overview: String? = null,
    @param:Json(name = "popularity") val popularity: Double? = null,
    @param:Json(name = "poster_path") val posterPath: String? = null,
    @param:Json(name = "release_date") val releaseDate: String? = null,
    @param:Json(name = "title") val title: String? = null,
    @param:Json(name = "video") val video: Boolean? = null,
    @param:Json(name = "vote_average") val voteAverage: Double? = null,
    @param:Json(name = "vote_count") val voteCount: Int? = null
) : Parcelable