package com.yunuscagliyan.home.home.viewmodel.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.RouteNavigationOptions
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.domain.GetMovieListByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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
        upComingMovies =
            getMovieListByType(type = MoviePagingType.UPCOMING).cachedIn(viewModelScope)
        trendingMovies =
            getMovieListByType(type = MoviePagingType.TRENDING).cachedIn(viewModelScope)
        popularMovies = getMovieListByType(type = MoviePagingType.POPULAR).cachedIn(viewModelScope)
        topRatedMovies =
            getMovieListByType(type = MoviePagingType.TOP_RATED).cachedIn(viewModelScope)
    }

    fun navigateList(pagingType: MoviePagingType) {
        sendEvent(
            event = CoreEvent.Navigation(
                Routes.NavigateToRoute(
                    pageRoute = RootScreenRoute.MovieList.navigate(listType = pagingType.index)
                )
            )
        )
    }

    fun onMovieTap(movieModel: MovieModel?) {
        movieModel?.id?.let { id ->
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