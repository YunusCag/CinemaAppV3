package com.yunuscagliyan.movie_detail.domain

import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.remote.response.MovieDetailResponse
import com.yunuscagliyan.core.domain.CoreRequestUseCase
import com.yunuscagliyan.core.helper.LanguageHelper
import com.yunuscagliyan.movie_detail.service.MovieDetailService
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val service: MovieDetailService
) : CoreRequestUseCase<GetMovieDetail.Params, MovieDetailResponse>() {
    data class Params(
        val movieId: Int,
        val language: String = LanguageType.EN.code,
        val region: String = "US"
    ) {
        fun getFullLanguage(): String = "$language-$region"
    }

    override suspend fun makeRequest(params: Params): MovieDetailResponse {
        return service.getMovieDetail(
            movieId = params.movieId,
            language = params.getFullLanguage()
        )
    }

}