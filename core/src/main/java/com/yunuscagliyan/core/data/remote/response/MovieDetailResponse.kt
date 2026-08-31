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
    @param:Json(name = "adult") var adult: Boolean? = false,
    @param:Json(name = "backdrop_path") var backdropPath: String? = null,
    @param:Json(name = "genres") var genres: List<GenreModel>? = null,
    @param:Json(name = "homepage") var homepage: String? = null,
    @param:Json(name = "imdb_id") var imdbId: String? = null,
    @param:Json(name = "original_language") var originalLanguage: String? = null,
    @param:Json(name = "original_title") var originalTitle: String? = null,
    @param:Json(name = "overview") var overview: String? = null,
    @param:Json(name = "popularity") var popularity: Double? = null,
    @param:Json(name = "poster_path") var posterPath: String? = null,
    @param:Json(name = "production_companies") var productionCompanies: List<ProductionCompanyModel>? = null,
    @param:Json(name = "production_countries") var productionCountries: List<ProductionCountryModel>? = null,
    @param:Json(name = "release_date") var releaseDate: String? = null,
    @param:Json(name = "budget") var budget: Long? = null,
    @param:Json(name = "revenue") var revenue: Long? = null,
    @param:Json(name = "runtime") var runtime: Int? = null,
    @param:Json(name = "spoken_languages") var spokenLanguages: List<SpokenLanguageModel>? = null,
    @param:Json(name = "status") var status: String? = null,
    @param:Json(name = "tagline") var tagline: String? = null,
    @param:Json(name = "title") var title: String? = null,
    @param:Json(name = "video") var video: Boolean? = null,
    @param:Json(name = "vote_average") var voteAverage: Double? = null,
    @param:Json(name = "vote_count") var voteCount: Int? = null
) : Parcelable
