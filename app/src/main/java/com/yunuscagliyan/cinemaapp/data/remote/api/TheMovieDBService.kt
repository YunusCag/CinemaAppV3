package com.yunuscagliyan.cinemaapp.data.remote.api

import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieResponse
import com.yunuscagliyan.cinemaapp.data.remote.url.*
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBService {

    @GET(GET_UP_COMING_MOVIE)
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreIds: List<Int>?,
    ): MovieResponse

    @GET(GET_POPULAR_MOVIE)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreIds: List<Int>?,
    ): MovieResponse

    @GET(GET_TRENDING_MOVIE_DAY)
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreIds: List<Int>?,
    ): MovieResponse

    @GET(GET_TOP_RATED_MOVIE)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreIds: List<Int>?,
    ): MovieResponse

}