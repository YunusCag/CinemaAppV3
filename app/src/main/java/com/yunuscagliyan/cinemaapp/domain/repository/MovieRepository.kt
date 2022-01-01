package com.yunuscagliyan.cinemaapp.domain.repository

import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieResponse

interface MovieRepository {

    suspend fun getUpComingMovies(
        page: Int,
        genreIds: List<Int>?,
    ): MovieResponse

    suspend fun getPopularMovies(
        page: Int,
        genreIds: List<Int>?,
    ): MovieResponse

    suspend fun getTrendingMovies(
        page: Int,
        genreIds: List<Int>?,
    ): MovieResponse

    suspend fun getTopRatedMovies(
        page: Int,
        genreIds: List<Int>?,
    ): MovieResponse
}