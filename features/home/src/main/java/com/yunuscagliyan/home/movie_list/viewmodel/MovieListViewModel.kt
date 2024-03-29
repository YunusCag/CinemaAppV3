package com.yunuscagliyan.home.movie_list.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.helper.LanguageHelper
import com.yunuscagliyan.core.helper.RegionHelper
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.LIST_TYPE_KEY
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.data.enum.MovieListGridColumn
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.domain.GetMovieGenreList
import com.yunuscagliyan.home.domain.GetMovieListByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListByType: GetMovieListByType,
    private val getGenreList: GetMovieGenreList,
    private val languageHelper: LanguageHelper,
    private val regionHelper: RegionHelper,
    savedStateHandle: SavedStateHandle
) : CoreViewModel() {

    var state = mutableStateOf(MovieListState())
    var movies: Flow<PagingData<MovieModel>> = emptyFlow()

    var listType: MoviePagingType = MoviePagingType.UPCOMING


    init {
        savedStateHandle.get<Int>(LIST_TYPE_KEY)?.let { listTypeIndex ->
            listType = MoviePagingType.fromIndex(listTypeIndex) ?: MoviePagingType.UPCOMING
            initState()
        }
    }

    private fun initState() {
        setState(state) {
            copy(
                listType = listType
            )
        }
        getMovies()
        getMovieGenreList()
    }

    fun getMovieGenreList() {
        getGenreList(
            GetMovieGenreList.Params(
                language = languageHelper.language.code,
                region = regionHelper.region.code
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    state.value = state.value.copy(
                        genreError = result.message,
                        isGenreLoading = false
                    )
                }
                is Resource.Loading -> {
                    state.value = state.value.copy(
                        isGenreLoading = true
                    )
                }
                is Resource.Success -> {
                    state.value = state.value.copy(
                        genreList = result.data?.genres ?: emptyList(),
                        selectedGenreIds = emptyList(),
                        isGenreLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovies() {
        movies = getMovieListByType(
            type = listType,
            genreIds = state.value.selectedGenreIds,
        ).cachedIn(viewModelScope)
    }


    fun changeColumnCount(column: MovieListGridColumn) {
        setState(state) {
            copy(
                columnCount = column
            )
        }
    }

    fun onGenreClick(genre: GenreModel, isSelected: Boolean) {
        setState(state) {
            val ids: MutableList<Int> = selectedGenreIds.toMutableList()
            if (isSelected) {
                ids.remove(genre.id)
            } else {
                genre.id?.let { id ->
                    ids.clear()
                    ids.add(id)
                }
            }
            copy(
                selectedGenreIds = ids
            )
        }

        getMovies()
    }

    fun onAllClick() {
        setState(state) {
            copy(
                selectedGenreIds = emptyList()
            )
        }
        getMovies()
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