package com.yunuscagliyan.movie_detail.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.data.remote.model.cast.CastModel
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.movie_detail.domain.GetCastCrew
import com.yunuscagliyan.movie_detail.domain.GetMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val getCastCrew: GetCastCrew,
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
        getMovieCastCrew(
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

    private fun getMovieCastCrew(id: Int) {
        getCastCrew(
            GetCastCrew.Params(
                movieId = id
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    setState(state) {
                        copy(
                            castLoading = false,
                            castError = result.message
                        )
                    }
                }
                is Resource.Loading -> {
                    setState(state) {
                        copy(
                            castLoading = true,
                        )
                    }
                }
                is Resource.Success -> {
                    setState(state) {
                        copy(
                            castLoading = false,
                            cast = result.data?.cast ?: emptyList(),
                            crew = result.data?.crew ?: emptyList(),
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onCastClick(castModel: CastModel) {
        //TODO Navigate Cast page
    }
}