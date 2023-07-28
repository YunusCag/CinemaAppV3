package com.yunuscagliyan.movie_detail.ui.video

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.ripple.NoRippleInteractionSource
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.movie_detail.viewmodel.video.VideoViewModel


object VideoScreen : CoreScreen<VideoViewModel>() {
    override val route: String
        get() = RootScreenRoute.Video.route

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(
            name = Constants.NavigationArgumentKey.VIDEO_ID_KEY
        ) {
            type = NavType.StringType
        },
        navArgument(
            name = Constants.NavigationArgumentKey.VIDEO_NAME_KEY
        ) {
            type = NavType.StringType
        }
    )

    @Composable
    override fun viewModel(): VideoViewModel = hiltViewModel()

    private var youtubePlayer: YouTubePlayerView? = null

    @SuppressLint("SourceLockedOrientationActivity")
    @Composable
    override fun Content(viewModel: VideoViewModel) {
        val state by viewModel.state
        val context = LocalContext.current

        val activity = context as Activity

        val onStateChange: (Boolean) -> Unit = remember {
            {
                viewModel.changeVideoState(it)
            }
        }

        DisposableEffect(key1 = Unit) {
            onDispose {
                youtubePlayer?.release()
                youtubePlayer = null
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CinemaAppTheme.colors.background)
        ) {
            YoutubePlayer(
                videoId = state.videoId,
                onStateChange = onStateChange
            )
            AnimatedVisibility(
                visible = !state.isVideoPlaying,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopBar(
                    videoName = state.videoName,
                    onBackPress = {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        viewModel.popBack()
                    }
                )
            }
        }
    }

    @Composable
    private fun YoutubePlayer(
        videoId: String?,
        onStateChange: (Boolean) -> Unit
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(color = CinemaAppTheme.colors.background),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(
                                videoId = videoId ?: EMPTY_STRING,
                                0f
                            )
                        }
                    })
                    setBackgroundColor(resources.getColor(android.R.color.black))
                    enterFullScreen()

                }
            },
            update = { player ->
                player.apply {
                    getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(
                                videoId = videoId ?: EMPTY_STRING,
                                0f
                            )
                            youTubePlayer.addListener(
                                listener = object : YouTubePlayerListener {
                                    override fun onApiChange(youTubePlayer: YouTubePlayer) = Unit

                                    override fun onCurrentSecond(
                                        youTubePlayer: YouTubePlayer,
                                        second: Float
                                    ) = Unit

                                    override fun onError(
                                        youTubePlayer: YouTubePlayer,
                                        error: PlayerConstants.PlayerError
                                    ) = Unit

                                    override fun onPlaybackQualityChange(
                                        youTubePlayer: YouTubePlayer,
                                        playbackQuality: PlayerConstants.PlaybackQuality
                                    ) = Unit

                                    override fun onPlaybackRateChange(
                                        youTubePlayer: YouTubePlayer,
                                        playbackRate: PlayerConstants.PlaybackRate
                                    ) = Unit

                                    override fun onReady(youTubePlayer: YouTubePlayer) = Unit

                                    override fun onStateChange(
                                        youTubePlayer: YouTubePlayer,
                                        state: PlayerConstants.PlayerState
                                    ) {
                                        onStateChange(state == PlayerConstants.PlayerState.PLAYING)
                                    }

                                    override fun onVideoDuration(
                                        youTubePlayer: YouTubePlayer,
                                        duration: Float
                                    ) = Unit

                                    override fun onVideoId(
                                        youTubePlayer: YouTubePlayer,
                                        videoId: String
                                    ) = Unit

                                    override fun onVideoLoadedFraction(
                                        youTubePlayer: YouTubePlayer,
                                        loadedFraction: Float
                                    ) = Unit

                                }
                            )
                        }
                    })
                    enterFullScreen()
                }
                youtubePlayer = player
            }
        )
    }

    @Composable
    fun TopBar(
        videoName: String?,
        onBackPress: () -> Unit
    ) {
        SimpleTopBar(
            title = videoName ?: EMPTY_STRING,
            backgroundColor = CinemaAppTheme.colors.blackColor.copy(
                alpha = 0.6f
            ),
            rightActions = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.common_back_button_description),
                    modifier = Modifier
                        .size(24.dp),
                    tint = Color.Transparent
                )
            },
            leftActions = {
                IconButton(
                    onClick = onBackPress,
                    interactionSource = NoRippleInteractionSource()
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.common_back_button_description),
                        modifier = Modifier
                            .size(24.dp),
                        tint = CinemaAppTheme.colors.whiteColor
                    )
                }
            },
        )
    }
}