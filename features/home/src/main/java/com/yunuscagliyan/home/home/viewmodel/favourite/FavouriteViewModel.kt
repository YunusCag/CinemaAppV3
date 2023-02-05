package com.yunuscagliyan.home.home.viewmodel.favourite

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.domain.repository.MovieRepository
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: MovieRepository
) : CoreViewModel() {

    val state = mutableStateOf(FavouriteState())

    init {
        initState()
    }

    private fun initState() {
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
}