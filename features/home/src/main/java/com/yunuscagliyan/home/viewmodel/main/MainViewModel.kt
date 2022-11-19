package com.yunuscagliyan.home.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core.util.Resource
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.domain.GetUpComingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUpComingMovies: GetUpComingMovies
) : CoreViewModel() {

    init {
        getMovies()
    }

    private fun getMovies() {
        getUpComingMovies(
            GetUpComingMovies.Params(
                page = 1
            )
        ).onEach {resource->
            Timber.d(resource.toString())
            when(resource){
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success ->{
                    Timber.e(resource.data?.results?.size.toString())
                }
            }

        }.launchIn(viewModelScope)
    }
}