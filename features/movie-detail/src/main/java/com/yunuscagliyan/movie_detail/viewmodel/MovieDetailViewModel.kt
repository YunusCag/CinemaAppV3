package com.yunuscagliyan.movie_detail.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.movie_detail.domain.GetMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    savedStateHandle: SavedStateHandle
) : CoreViewModel() {

    val state = mutableStateOf(MovieDetailState())

    init {
        savedStateHandle.get<Int>(MOVIE_ID_KEY)?.let { movieId ->
            initState(movieId)
        }
    }

    private fun initState(movieId: Int) {
        getMovieDetailResponse(
            id = movieId
        )
    }

    private fun getMovieDetailResponse(id: Int) {
        getMovieDetail(
            GetMovieDetail.Params(
                movieId = id
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    setState(state) {
                        copy(
                            movieDetailError = result.message,
                            movieDetailLoading = false
                        )
                    }
                }
                is Resource.Loading -> {
                    setState(state) {
                        copy(
                            movieDetailLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    setState(state) {
                        copy(
                            movieDetailLoading = false,
                            movieDetailResponse = result.data
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}