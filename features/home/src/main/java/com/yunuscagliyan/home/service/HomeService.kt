package com.yunuscagliyan.home.service

import com.yunuscagliyan.core.data.remote.response.MovieListResponse
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.NetworkUtil.UPCOMING_MOVIES_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET(UPCOMING_MOVIES_URL)
    suspend fun getUpComingMovie(
        @Query(Constants.NetworkQueryParamKey.page) page: Int,
        @Query(Constants.NetworkQueryParamKey.language) lang: String,
    ): MovieListResponse
}