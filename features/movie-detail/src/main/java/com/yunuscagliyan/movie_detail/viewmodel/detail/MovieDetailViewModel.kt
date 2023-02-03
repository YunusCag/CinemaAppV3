package com.yunuscagliyan.movie_detail.viewmodel.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.enums.VideoSite
import com.yunuscagliyan.core.data.enums.VideoType
import com.yunuscagliyan.core.data.remote.model.cast.CastModel
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.MOVIE_ID_KEY
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.movie_detail.domain.GetCastCrew
import com.yunuscagliyan.movie_detail.domain.GetMovieDetail
import com.yunuscagliyan.movie_detail.domain.GetMovieVideo
import com.yunuscagliyan.movie_detail.domain.GetSimilarMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val getCastCrew: GetCastCrew,
    private val getSimilarMovies: GetSimilarMovies,
    private val getMovieVideo: GetMovieVideo,
    savedStateHandle: SavedStateHandle
) : CoreViewModel() {

    val state = mutableStateOf(MovieDetailState())
    var similarMovies: Flow<PagingData<MovieModel>> = emptyFlow()

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
        getSimilar(
            id = movieId
        )
        getVideo(
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

    private fun getSimilar(id: Int) {
        similarMovies = getSimilarMovies(
            movieId = id
        ).cachedIn(viewModelScope)
    }

    private fun getVideo(id: Int) {
        getMovieVideo(
            GetMovieVideo.Params(
                movieId = id
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    setState(state) {
                        copy(
                            videoLoading = false,
                            videoError = result.message
                        )
                    }
                }
                is Resource.Loading -> {
                    setState(state) {
                        copy(
                            videoLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    setState(state) {
                        var rawVideoList = result.data?.results ?: emptyList()
                        rawVideoList = rawVideoList.filter {
                            it.type == VideoType.Trailer.type && it.site == VideoSite.Youtube.site
                        }.toList()

                        copy(
                            videoLoading = false,
                            videoList = rawVideoList
                        )
                    }

                }
            }
        }.launchIn(viewModelScope)
    }

    fun onCastClick(castModel: CastModel) {
        //TODO Navigate Cast page
    }

    fun onTeaserClick() {
        state.value.videoList.randomOrNull()?.let { movieVideoModel ->
            movieVideoModel.key?.let { key ->
                sendEvent(
                    CoreEvent.Navigation(
                        Routes.NavigateToRoute(
                            pageRoute = RootScreenRoute.Video.navigate(
                                videoId = key,
                                videoName = movieVideoModel.name ?: EMPTY_STRING
                            )
                        )
                    )
                )
            }
        }
    }

    fun onMovieClick(model: MovieModel?) {
        model?.id?.let { id ->
            sendEvent(
                CoreEvent.Navigation(
                    Routes.NavigateToRoute(
                        pageRoute = RootScreenRoute.MovieDetail.navigate(id)
                    )
                )
            )
        }
    }
}