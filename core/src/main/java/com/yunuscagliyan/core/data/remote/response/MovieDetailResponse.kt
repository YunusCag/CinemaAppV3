package com.yunuscagliyan.core.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.company.ProductionCompanyModel
import com.yunuscagliyan.core.data.remote.model.country.ProductionCountryModel
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.data.remote.model.language.SpokenLanguageModel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    @Json(name = "adult") var adult: Boolean? = false,
    @Json(name = "backdrop_path") var backdropPath: String? = null,
    @Json(name = "genres") var genres: List<GenreModel>? = null,
    @Json(name = "homepage") var homepage: String? = null,
    @Json(name = "imdb_id") var imdbId: String? = null,
    @Json(name = "original_language") var originalLanguage: String? = null,
    @Json(name = "original_title") var originalTitle: String? = null,
    @Json(name = "overview") var overview: String? = null,
    @Json(name = "popularity") var popularity: Double? = null,
    @Json(name = "poster_path") var posterPath: String? = null,
    @Json(name = "production_companies") var productionCompanies: List<ProductionCompanyModel>? = null,
    @Json(name = "production_countries") var productionCountries: List<ProductionCountryModel>? = null,
    @Json(name = "release_date") var releaseDate: String? = null,
    @Json(name = "budget") var budget: Int? = null,
    @Json(name = "revenue") var revenue: Int? = null,
    @Json(name = "runtime") var runtime: Int? = null,
    @Json(name = "spoken_languages") var spokenLanguages: List<SpokenLanguageModel>? = null,
    @Json(name = "status") var status: String? = null,
    @Json(name = "tagline") var tagline: String? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "video") var video: Boolean? = null,
    @Json(name = "vote_average") var voteAverage: Double? = null,
    @Json(name = "vote_count") var voteCount: Int? = null
) : Parcelable
