package com.yunuscagliyan.home.viewmodel.main

import androidx.paging.PagingData
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.domain.GetMovieListByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieListByType: GetMovieListByType
) : CoreViewModel() {

    var upComingMovies: Flow<PagingData<MovieModel>> = emptyFlow()
    var trendingMovies: Flow<PagingData<MovieModel>> = emptyFlow()
    var popularMovies: Flow<PagingData<MovieModel>> = emptyFlow()
    var topRatedMovies: Flow<PagingData<MovieModel>> = emptyFlow()


    init {
        getMovies()
    }

    private fun getMovies() {
        upComingMovies = getMovieListByType(type = MoviePagingType.UPCOMING)
        trendingMovies = getMovieListByType(type = MoviePagingType.TRENDING)
        popularMovies = getMovieListByType(type = MoviePagingType.POPULAR)
        topRatedMovies = getMovieListByType(type = MoviePagingType.TOP_RATED)
    }
}