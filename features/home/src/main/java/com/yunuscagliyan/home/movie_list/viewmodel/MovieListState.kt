package com.yunuscagliyan.home.movie_list.viewmodel

import com.yunuscagliyan.home.data.enum.MoviePagingType

data class MovieListState(
    var listType: MoviePagingType = MoviePagingType.UPCOMING
)
