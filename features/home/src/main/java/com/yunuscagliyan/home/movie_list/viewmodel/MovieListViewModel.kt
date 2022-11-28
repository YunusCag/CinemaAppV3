package com.yunuscagliyan.home.movie_list.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.util.Constants.NavigationArgumentKey.LIST_TYPE_KEY
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.data.enum.MoviePagingType
import com.yunuscagliyan.home.domain.GetMovieListByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListByType: GetMovieListByType,
    savedStateHandle: SavedStateHandle
) : CoreViewModel() {

    var state = mutableStateOf(MovieListState())
    var movies: Flow<PagingData<MovieModel>> = emptyFlow()

    init {
        savedStateHandle.get<Int>(LIST_TYPE_KEY)?.let { listTypeIndex ->
            initState(
                listType = MoviePagingType.fromIndex(listTypeIndex) ?: MoviePagingType.UPCOMING
            )
        }
    }

    private fun initState(listType: MoviePagingType) {
        state.value = state.value.copy(
            listType = listType
        )
        movies = getMovieListByType(
            type = listType
        ).cachedIn(viewModelScope)
    }
}