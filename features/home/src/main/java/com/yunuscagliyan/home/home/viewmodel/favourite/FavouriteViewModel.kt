package com.yunuscagliyan.home.home.viewmodel.favourite

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.data.local.entity.MovieEntity
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.domain.repository.MovieRepository
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: MovieRepository
) : CoreViewModel() {

    val state = mutableStateOf(FavouriteState())

    private val _favouriteEvent = Channel<FavouriteEvent>()
    val favouriteEvent = _favouriteEvent.receiveAsFlow()

    fun initState() {
        repository.getAllMovies()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        setState(state) {
                            copy(
                                favouriteLoading = true
                            )
                        }
                    }
                    is Resource.Error -> {
                        setState(state) {
                            copy(
                                favouriteLoading = false,
                                favouriteError = result.message
                            )
                        }
                    }
                    is Resource.Success -> {
                        setState(state) {
                            copy(
                                favouriteLoading = false,
                                favouriteError = null,
                                favourites = result.data ?: emptyList()
                            )
                        }
                    }
                }

            }
            .launchIn(viewModelScope)
    }

    fun onMovieTap(movieModel: MovieModel) {
        movieModel.id?.let { id ->
            sendEvent(
                CoreEvent.Navigation(
                    Routes.NavigateToRoute(
                        pageRoute = RootScreenRoute.MovieDetail.navigate(id)
                    )
                )
            )
        }
    }

    fun onRevokeClick(index: Int,entity: MovieEntity) {
        viewModelScope.launch {
            repository.insertMovie(
                movie = entity
            )

            setState(state) {
                val movies = favourites.toMutableList()
                movies.add(index, entity)
                copy(
                    favourites = movies.toList()
                )
            }
        }
    }

    fun onDismiss(index: Int, entity: MovieEntity) {
        Timber.e("index:$index ---> $entity")
        entity.movieId?.let { movieId ->
            viewModelScope.launch {
                repository.deleteMovie(
                    movieId = movieId
                )
            }

            changeEvent(
                event = FavouriteEvent.ShowSnackBar(
                    entity = entity,
                    index = index
                )
            )
            setState(state) {
                val favouriteList = favourites.toMutableList()
                favouriteList.remove(entity)
                copy(
                    favourites = favouriteList.toList()
                )
            }
        }
    }

    private fun changeEvent(event: FavouriteEvent) {
        viewModelScope.launch {
            _favouriteEvent.send(event)
        }
    }

    sealed class FavouriteEvent {
        data class ShowSnackBar(val index:Int,val entity: MovieEntity) : FavouriteEvent()
    }
}