package com.yunuscagliyan.cinemaapp.domain.use_case.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yunuscagliyan.cinemaapp.core.util.Resource
import com.yunuscagliyan.cinemaapp.data.remote.enum.MoviePagingType
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieResponse
import com.yunuscagliyan.cinemaapp.data.remote.paging.MoviePagingSource
import com.yunuscagliyan.cinemaapp.data.remote.url.PER_PAGE_ITEM
import com.yunuscagliyan.cinemaapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetUpComingMovies @Inject constructor(
    private val repository: MovieRepository,
) {

    operator fun invoke(
        genreIds: List<Int>? = null,
    ) = Pager(
        config = PagingConfig(PER_PAGE_ITEM),
        pagingSourceFactory = {
            MoviePagingSource(
                repository = repository,
                type = MoviePagingType.UPCOMING,
                genreIds = genreIds,
            )
        }
    ).flow
}