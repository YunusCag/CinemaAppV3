package com.yunuscagliyan.home.data.source

import androidx.paging.PagingState
import com.yunuscagliyan.core.data.paging.BaseMovieDataSource
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.data.remote.response.MovieListResponse
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.service.HomeService

class MovieDataSource(
    private val service: HomeService,
    private val params: Param = Param(),
    private val type: MoviePagingType
) : BaseMovieDataSource() {
    data class Param(
        var genreIds: List<Int>? = null,
        val language: String = "en",
        val region: String = "US",
    )

    override suspend fun makeRequest(currentPage: Int): MovieListResponse {
        return when (type) {
            MoviePagingType.UPCOMING -> service.getUpComingMovie(
                page = currentPage,
                lang = params.language,
                region = params.region,
                genreIds = params.genreIds
            )
            MoviePagingType.TRENDING -> service.getTrendingMovie(
                page = currentPage,
                lang = params.language,
                region = params.region,
                genreIds = params.genreIds
            )
            MoviePagingType.POPULAR -> service.getPopularMovie(
                page = currentPage,
                lang = params.language,
                region = params.region,
                genreIds = params.genreIds
            )
            MoviePagingType.TOP_RATED -> service.getTopRatedMovie(
                page = currentPage,
                lang = params.language,
                region = params.region,
                genreIds = params.genreIds
            )
        }
    }
}