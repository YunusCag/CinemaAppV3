package com.yunuscagliyan.movie_detail.service

import com.yunuscagliyan.core.data.remote.response.MovieDetailResponse
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.NetworkUtil.MOVIE_DETAIL_URL
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {

    @GET(MOVIE_DETAIL_URL)
    suspend fun getMovieDetail(
        @Path(Constants.NetworkQueryParamKey.movieId) movieId: Int,
        @Query(Constants.NetworkQueryParamKey.language) language: String
    ): MovieDetailResponse

}