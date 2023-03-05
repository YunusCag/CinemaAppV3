package com.yunuscagliyan.movie_detail.domain

import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.remote.response.CastCrewResponse
import com.yunuscagliyan.core.domain.CoreRequestUseCase
import com.yunuscagliyan.movie_detail.service.MovieDetailService
import javax.inject.Inject

class GetCastCrew @Inject constructor(
    private val service: MovieDetailService
) : CoreRequestUseCase<GetCastCrew.Params, CastCrewResponse>() {

    data class Params(
        val movieId: Int,
        val language: String = LanguageType.EN.code,
        val region: String = "US"
    ) {
        fun getFullLanguage(): String = "$language-$region"
    }

    override suspend fun makeRequest(params: Params): CastCrewResponse {
        return service.getMovieCastCrew(
            movieId = params.movieId,
            language = params.getFullLanguage()
        )
    }
}