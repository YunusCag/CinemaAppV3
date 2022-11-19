package com.yunuscagliyan.home.domain

import com.yunuscagliyan.core.data.remote.response.MovieListResponse
import com.yunuscagliyan.core.domain.CoreRequestUseCase
import com.yunuscagliyan.home.service.HomeService
import javax.inject.Inject

class GetUpComingMovies @Inject constructor(
    private val service: HomeService
) : CoreRequestUseCase<GetUpComingMovies.Params, MovieListResponse>() {
    data class Params(
        val page: Int,
        val language: String = "en"
    )

    override suspend fun makeRequest(params: Params): MovieListResponse {
        return service.getUpComingMovie(
            page = params.page,
            lang = params.language
        )
    }
}