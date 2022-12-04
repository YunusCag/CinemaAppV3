package com.yunuscagliyan.home.movie_list.viewmodel

import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.util.UIText
import com.yunuscagliyan.home.data.enum.MovieListGridColumn
import com.yunuscagliyan.home.data.enum.MoviePagingType

data class MovieListState(
    val listType: MoviePagingType = MoviePagingType.UPCOMING,
    val columnCount: MovieListGridColumn = MovieListGridColumn.SINGLE_ITEM,
    val genreList: List<GenreModel> = emptyList(),
    val selectedGenreIds: List<Int> = emptyList(),
    val isGenreLoading: Boolean = true,
    val genreError: UIText? = null
)
