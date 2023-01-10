package com.yunuscagliyan.movie_detail.service

import com.yunuscagliyan.core.data.remote.response.CastCrewResponse
import com.yunuscagliyan.core.data.remote.response.MovieDetailResponse
import com.yunuscagliyan.core.data.remote.response.MovieListResponse
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.NetworkUtil.MOVIE_CREDIT_URL
import com.yunuscagliyan.core.util.Constants.NetworkUtil.MOVIE_DETAIL_URL
import com.yunuscagliyan.core.util.Constants.NetworkUtil.SIMILAR_MOVIES_URL
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {

    @GET(MOVIE_DETAIL_URL)
    suspend fun getMovieDetail(
        @Path(Constants.NetworkQueryParamKey.movieId) movieId: Int,
        @Query(Constants.NetworkQueryParamKey.language) language: String
    ): MovieDetailResponse

    @GET(MOVIE_CREDIT_URL)
    suspend fun getMovieCastCrew(
        @Path(Constants.NetworkQueryParamKey.movieId) movieId: Int,
        @Query(Constants.NetworkQueryParamKey.language) language: String
    ): CastCrewResponse

    @GET(SIMILAR_MOVIES_URL)
    suspend fun getSimilarMovies(
        @Path(Constants.NetworkQueryParamKey.movieId) movieId: Int,
        @Query(Constants.NetworkQueryParamKey.page) page: Int,
        @Query(Constants.NetworkQueryParamKey.language) language: String
    ): MovieListResponse

}