package com.yunuscagliyan.cinemaapp.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.cinemaapp.core.util.Resource
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetPopularMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetTopRatedMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetTrendingMovies
import com.yunuscagliyan.cinemaapp.domain.use_case.movie.GetUpComingMovies
import com.yunuscagliyan.cinemaapp.presentation.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val upComing: GetUpComingMovies,
    private val trending: GetTrendingMovies,
    private val popular: GetPopularMovies,
    private val topRated: GetTopRatedMovies,
) : ViewModel() {

    var upComingMovies = mutableStateOf<List<MovieModel?>>(listOf())
    var upComingState = mutableStateOf<NetworkState>(NetworkState.Loading)

    var trendingMovies = mutableStateOf<List<MovieModel?>>(listOf())
    var trendingState = mutableStateOf<NetworkState>(NetworkState.Loading)

    var popularMovies = mutableStateOf<List<MovieModel?>>(listOf())
    var popularState = mutableStateOf<NetworkState>(NetworkState.Loading)

    var topRatedMovies = mutableStateOf<List<MovieModel?>>(listOf())
    var topRatedState = mutableStateOf<NetworkState>(NetworkState.Loading)


    init {
        getUpComingMovies()
        getTrendingMovies()
        getPopularMovies()
        getTopRatedMovies()
    }

    private fun getUpComingMovies() = viewModelScope.launch(Dispatchers.IO) {
        upComing().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    upComingState.value = NetworkState.Error(resource.message)
                }
                is Resource.Loading -> {
                    upComingState.value = NetworkState.Loading
                }
                is Resource.Success -> {
                    upComingState.value = NetworkState.Success
                    val response = resource.data
                    response?.results?.let {
                        upComingMovies.value = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTrendingMovies() = viewModelScope.launch(Dispatchers.IO) {
        trending().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    trendingState.value = NetworkState.Error(resource.message)
                }
                is Resource.Loading -> {
                    trendingState.value = NetworkState.Loading
                }
                is Resource.Success -> {
                    trendingState.value = NetworkState.Success
                    val response = resource.data
                    response?.results?.let {
                        trendingMovies.value = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        popular().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    popularState.value = NetworkState.Error(resource.message)
                }
                is Resource.Loading -> {
                    popularState.value = NetworkState.Loading
                }
                is Resource.Success -> {
                    popularState.value = NetworkState.Success
                    val response = resource.data
                    response?.results?.let {
                        popularMovies.value = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies() = viewModelScope.launch(Dispatchers.IO) {
        topRated().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    topRatedState.value = NetworkState.Error(resource.message)
                }
                is Resource.Loading -> {
                    topRatedState.value = NetworkState.Loading
                }
                is Resource.Success -> {
                    topRatedState.value = NetworkState.Success
                    val response = resource.data
                    response?.results?.let {
                        topRatedMovies.value = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}