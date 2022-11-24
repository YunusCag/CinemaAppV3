package com.yunuscagliyan.home.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.util.Constants.PaginationUtil.PER_PAGE_ITEM
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.data.source.MovieDataSource
import com.yunuscagliyan.home.service.HomeService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieListByType @Inject constructor(
    private val service: HomeService,
) {
    operator fun invoke(
        genreIds: List<Int>?=null,
        type: MoviePagingType
    ): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                PER_PAGE_ITEM
            ),
            pagingSourceFactory = {
                MovieDataSource(
                    service = service,
                    params = MovieDataSource.Param(
                        genreIds = genreIds
                    ),
                    type = type
                )
            }
        ).flow
    }
}