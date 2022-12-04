package com.yunuscagliyan.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.data.remote.response.MovieListResponse

abstract class BaseMovieDataSource() : PagingSource<Int, MovieModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val current = params.key ?: 1
        return try {
            val response = makeRequest(
                current,
            )
            LoadResult.Page(
                data = response.results.orEmpty(),
                prevKey = if (current == 1) null else current - 1,
                nextKey = if (response.results?.isEmpty() == true) null else current + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    abstract suspend fun makeRequest(currentPage: Int): MovieListResponse

}