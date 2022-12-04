package com.yunuscagliyan.home.service

import com.yunuscagliyan.core.data.remote.response.GenreListResponse
import com.yunuscagliyan.core.data.remote.response.MovieListResponse
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.NetworkUtil.GENRE_LIST_MOVIES_URL
import com.yunuscagliyan.core.util.Constants.NetworkUtil.POPULAR_MOVIES_URL
import com.yunuscagliyan.core.util.Constants.NetworkUtil.TOP_RATED_MOVIES_URL
import com.yunuscagliyan.core.util.Constants.NetworkUtil.TRENDING_MOVIES_URL
import com.yunuscagliyan.core.util.Constants.NetworkUtil.UPCOMING_MOVIES_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET(UPCOMING_MOVIES_URL)
    suspend fun getUpComingMovie(
        @Query(Constants.NetworkQueryParamKey.page) page: Int,
        @Query(Constants.NetworkQueryParamKey.language) lang: String,
        @Query(Constants.NetworkQueryParamKey.region) region: String?,
        @Query(Constants.NetworkQueryParamKey.withGenres) genreIds: List<Int>?,
    ): MovieListResponse

    @GET(POPULAR_MOVIES_URL)
    suspend fun getPopularMovie(
        @Query(Constants.NetworkQueryParamKey.page) page: Int,
        @Query(Constants.NetworkQueryParamKey.language) lang: String,
        @Query(Constants.NetworkQueryParamKey.region) region: String?,
        @Query(Constants.NetworkQueryParamKey.withGenres) genreIds: List<Int>?,
    ): MovieListResponse

    @GET(TRENDING_MOVIES_URL)
    suspend fun getTrendingMovie(
        @Query(Constants.NetworkQueryParamKey.page) page: Int,
        @Query(Constants.NetworkQueryParamKey.language) lang: String,
        @Query(Constants.NetworkQueryParamKey.region) region: String?,
        @Query(Constants.NetworkQueryParamKey.withGenres) genreIds: List<Int>?,
    ): MovieListResponse

    @GET(TOP_RATED_MOVIES_URL)
    suspend fun getTopRatedMovie(
        @Query(Constants.NetworkQueryParamKey.page) page: Int,
        @Query(Constants.NetworkQueryParamKey.language) lang: String,
        @Query(Constants.NetworkQueryParamKey.region) region: String?,
        @Query(Constants.NetworkQueryParamKey.withGenres) genreIds: List<Int>?,
    ): MovieListResponse

    @GET(GENRE_LIST_MOVIES_URL)
    suspend fun getMovieGenreList(
        @Query(Constants.NetworkQueryParamKey.language) lang: String
    ): GenreListResponse


}