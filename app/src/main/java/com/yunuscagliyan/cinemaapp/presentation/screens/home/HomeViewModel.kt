package com.yunuscagliyan.cinemaapp.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yunuscagliyan.cinemaapp.core.util.Resource
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetPopularMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetTopRatedMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetTrendingMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetUpComingMovies
import com.yunuscagliyan.cinemaapp.presentation.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    upComing: GetUpComingMovies,
    trending: GetTrendingMovies,
    popular: GetPopularMovies,
    topRated: GetTopRatedMovies,
) : ViewModel() {

    var upComingMovies: Flow<PagingData<MovieModel>> = upComing().cachedIn(viewModelScope)

    var trendingMovies: Flow<PagingData<MovieModel>> = trending().cachedIn(viewModelScope)

    var popularMovies: Flow<PagingData<MovieModel>> = popular().cachedIn(viewModelScope)

    var topRatedMovies: Flow<PagingData<MovieModel>> = topRated().cachedIn(viewModelScope)

}