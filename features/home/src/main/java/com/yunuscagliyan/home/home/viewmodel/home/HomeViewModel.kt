package com.yunuscagliyan.home.home.viewmodel.home

import com.yunuscagliyan.core.data.remote.model.movie.MovieModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.data.enum.MoviePagingType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : CoreViewModel() {

    fun navigateList(pagingType: MoviePagingType){
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