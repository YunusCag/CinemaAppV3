package com.yunuscagliyan.movie_detail.source

import com.yunuscagliyan.core.data.paging.BaseMovieDataSource
import com.yunuscagliyan.core.data.remote.response.MovieListResponse
import com.yunuscagliyan.movie_detail.service.MovieDetailService

class SimilarMoviesDataSource(
    private val service: MovieDetailService,
    private val params: Param
) : BaseMovieDataSource() {

    data class Param(
        val movieId: Int,
        val language: String = "en-US",
    )

    override suspend fun makeRequest(currentPage: Int): MovieListResponse {
        return service.getSimilarMovies(
            movieId = params.movieId,
            page = currentPage,
            language = params.language
        )
    }
}