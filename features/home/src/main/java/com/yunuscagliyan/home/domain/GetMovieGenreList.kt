package com.yunuscagliyan.home.domain

import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.remote.response.GenreListResponse
import com.yunuscagliyan.core.domain.CoreRequestUseCase
import com.yunuscagliyan.core.util.Constants.LanguageUtil.EN_US
import com.yunuscagliyan.home.service.HomeService
import javax.inject.Inject

class GetMovieGenreList @Inject constructor(
    private val service: HomeService
) : CoreRequestUseCase<GetMovieGenreList.Params, GenreListResponse>() {
    data class Params(
        val language: String = LanguageType.EN.code,
        val region: String = "US"
    ) {
        fun getFullLanguage(): String = "$language-$region"
    }

    override suspend fun makeRequest(params: Params): GenreListResponse {
        return service.getMovieGenreList(
            lang = params.getFullLanguage()
        )
    }
}