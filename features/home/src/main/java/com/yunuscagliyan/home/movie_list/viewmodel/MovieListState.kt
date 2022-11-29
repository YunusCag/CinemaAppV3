package com.yunuscagliyan.home.movie_list.viewmodel

import com.yunuscagliyan.home.data.enum.MovieListGridColumn
import com.yunuscagliyan.home.data.enum.MoviePagingType

data class MovieListState(
    var listType: MoviePagingType = MoviePagingType.UPCOMING,
    var columnCount: MovieListGridColumn = MovieListGridColumn.SINGLE_ITEM
)
