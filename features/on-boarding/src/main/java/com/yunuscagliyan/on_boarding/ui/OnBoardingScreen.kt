package com.yunuscagliyan.on_boarding.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.yunuscagliyan.core.data.ui.OnBoardingModel
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core_ui.components.button.SecondaryMediumTextButton
import com.yunuscagliyan.core_ui.components.introduce.IntroducePage
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.core.R
import com.yunuscagliyan.on_boarding.viewmodel.OnBoardingViewModel
import kotlinx.coroutines.launch


object OnBoardingScreen : CoreScreen<OnBoardingViewModel>() {
    override val route: String
        get() = RootScreenRoute.OnBoarding.route

    @Composable
    override fun viewModel(): OnBoardingViewModel = hiltViewModel()

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    override fun Content(viewModel: OnBoardingViewModel) {
        val state by viewModel.state
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()



        MainUIFrame() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    IntroductionPager(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = 25.dp
                            ),
                        pagerState = pagerState,
                        introduceList = state.introduceList
                    )
                    Box(
                        modifier = Modifier
                            .noRippleClickable {
                                if (pagerState.pageCount > pagerState.currentPage) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % (pagerState.pageCount))
                                    }
                                }
                            }
                            .background(CinemaAppTheme.colors.background, CircleShape)
                            .padding(6.dp)
                            .size(50.dp)
                            .background(CinemaAppTheme.colors.secondary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp),
                            tint = CinemaAppTheme.colors.whiteColor
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(CinemaAppTheme.colors.background)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 24.dp
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    SecondaryMediumTextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.common_skip),
                        onClick = viewModel::saveShowShouldPreference
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun IntroductionPager(
        modifier: Modifier = Modifier,
        pagerState: PagerState,
        introduceList: List<OnBoardingModel>
    ) {
        Column(
            modifier = modifier
                .background(
                    CinemaAppTheme.colors.card,
                    CinemaAppTheme.shapes.bottomRoundedLargeShape
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier
                    .weight(1f),
                count = introduceList.size,
                state = pagerState
            ) { index ->
                val model = introduceList[index]
                IntroducePage(
                    title = stringResource(id = model.title),
                    description = stringResource(id = model.description),
                    image = model.image
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = CinemaAppTheme.colors.secondary,
                inactiveColor = CinemaAppTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}