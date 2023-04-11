package com.yunuscagliyan.on_boarding.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.data.ui.OnBoardingModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.RouteNavigationOptions
import com.yunuscagliyan.core_ui.navigation.Routes
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val preferences: Preferences
) : CoreViewModel() {

    val state = mutableStateOf(OnBoardingState())

    init {
        initState()
    }

    private fun initState() {
        setState(state) {
            val temp = listOf<OnBoardingModel>(
                OnBoardingModel(
                    title = R.string.on_boarding_first_title,
                    description = R.string.on_boarding_first_description,
                    image = R.drawable.introduce_page_first
                ),
                OnBoardingModel(
                    title = R.string.on_boarding_second_title,
                    description = R.string.on_boarding_second_description,
                    image = R.drawable.introduce_page_second
                ),
                OnBoardingModel(
                    title = R.string.on_boarding_third_title,
                    description = R.string.on_boarding_third_description,
                    image = R.drawable.introduce_page_third
                ),
            )
            copy(
                introduceList = temp
            )
        }
    }

    fun saveShowShouldPreference() {
        preferences.shouldShowOnBoard = false
        sendEvent(
            CoreEvent.Navigation(
                Routes.NavigateToRoute(
                    RootScreenRoute.Main.route,
                    options = RouteNavigationOptions(
                        RootScreenRoute.OnBoarding.route,
                        inclusive = true
                    )
                )
            )
        )
    }
}