package com.yunuscagliyan.movie_detail.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.movie_detail.service.MovieDetailService
import com.yunuscagliyan.movie_detail.source.SimilarMoviesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    private val service: MovieDetailService
) {
    operator fun invoke(
        movieId: Int
    ): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                Constants.PaginationUtil.PER_PAGE_ITEM
            ),
            pagingSourceFactory = {
                SimilarMoviesDataSource(
                    service = service,
                    params = SimilarMoviesDataSource.Param(
                        movieId = movieId,
                    )
                )
            }
        ).flow
    }
}