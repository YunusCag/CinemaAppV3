package com.yunuscagliyan.cinemaapp.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.cinemaapp.core.util.Resource
import com.yunuscagliyan.cinemaapp.data.remote.model.movie.MovieModel
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
) : ViewModel() {

    var upComingMovies = mutableStateOf<List<MovieModel?>>(listOf())
    var upComingState = mutableStateOf<NetworkState>(NetworkState.Loading)

    init {
        getUpComingMovies()
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
}