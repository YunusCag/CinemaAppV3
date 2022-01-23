package com.yunuscagliyan.cinemaapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yunuscagliyan.cinemaapp.data.remote.enum.MoviePagingType
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.domain.repository.MovieRepository

class MoviePagingSource(
    private val repository: MovieRepository,
    private val type: MoviePagingType,
    private val genreIds: List<Int>? = null,
) : PagingSource<Int, MovieModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val current = params.key ?: 1
        val response = when (type) {
            MoviePagingType.UPCOMING -> {
                repository.getUpComingMovies(current,genreIds)
            }
            MoviePagingType.TRENDING ->{
                repository.getTrendingMovies(current,genreIds)

            }
            MoviePagingType.POPULAR -> {
                repository.getPopularMovies(current,genreIds)
            }
            MoviePagingType.TOP_RATED -> {
                repository.getTopRatedMovies(current,genreIds)
            }
        }

        return LoadResult.Page(
            data = response.results.orEmpty(),
            prevKey = if (current==1) null else current-1,
            nextKey = if(response.results?.isEmpty()==true)null else current+1
        )
    }

}