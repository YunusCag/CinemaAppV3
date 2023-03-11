package com.yunuscagliyan.home.domain

import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.enums.RegionType
import com.yunuscagliyan.core.data.remote.response.GenreListResponse
import com.yunuscagliyan.core.domain.CoreRequestUseCase
import com.yunuscagliyan.core.helper.RegionHelper
import com.yunuscagliyan.core.util.Constants.LanguageUtil.EN_US
import com.yunuscagliyan.home.service.HomeService
import javax.inject.Inject

class GetMovieGenreList @Inject constructor(
    private val service: HomeService,
    private val regionHelper: RegionHelper
) : CoreRequestUseCase<GetMovieGenreList.Params, GenreListResponse>() {
    data class Params(
        val language: String = LanguageType.EN.code,
        val region: String = RegionType.USA.code
    ) {
        fun getFullLanguage(): String = "$language-$region"
    }

    override suspend fun makeRequest(params: Params): GenreListResponse {
        return service.getMovieGenreList(
            lang = params.language,
            region = params.region
        )
    }
}