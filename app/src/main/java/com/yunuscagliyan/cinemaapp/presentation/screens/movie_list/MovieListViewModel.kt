package com.yunuscagliyan.cinemaapp.presentation.screens.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.domain.enum.MovieListType
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetPopularMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetTopRatedMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetTrendingMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetUpComingMovies
import com.yunuscagliyan.cinemaapp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val upComing: GetUpComingMovies,
    private val trending: GetTrendingMovies,
    private val popular: GetPopularMovies,
    private val topRated: GetTopRatedMovies,
) : ViewModel() {

    val listType: Int? =
        savedStateHandle.get<Int>(Screen.MovieListScreen.ARGUMENT_KEY_TYPE_ID)


    var movies: Flow<PagingData<MovieModel>> = when (MovieListType.values()[listType?:0]) {
        MovieListType.UP_COMING -> {
            upComing.invoke()
        }
        MovieListType.TRENDING -> {
            trending.invoke()
        }
        MovieListType.POPULAR -> {
            popular.invoke()
        }
        MovieListType.TOP_RATED -> {
            topRated.invoke()
        }
        else -> {
            upComing.invoke()
        }
    }.cachedIn(viewModelScope)


}