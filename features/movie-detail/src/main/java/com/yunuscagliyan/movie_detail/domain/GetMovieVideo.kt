package com.yunuscagliyan.movie_detail.domain

import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.remote.response.MovieVideoResponse
import com.yunuscagliyan.core.domain.CoreRequestUseCase
import com.yunuscagliyan.movie_detail.service.MovieDetailService
import javax.inject.Inject

class GetMovieVideo @Inject constructor(
    private val service: MovieDetailService
) : CoreRequestUseCase<GetMovieVideo.Params, MovieVideoResponse>() {
    data class Params(
        val movieId: Int,
        val language: String = LanguageType.EN.code,
        val region: String = "US"
    ) {
        fun getFullLanguage(): String = "$language-$region"
    }

    override suspend fun makeRequest(params: Params): MovieVideoResponse {
        return service.getMovieVideo(
            movieId = params.movieId,
            language = params.getFullLanguage()
        )
    }
}