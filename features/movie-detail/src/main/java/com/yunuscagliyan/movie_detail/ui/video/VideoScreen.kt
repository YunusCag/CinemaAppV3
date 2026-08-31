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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.yunuscagliyan.core.R
import timber.log.Timber
import com.yunuscagliyan.core.navigation.RootScreenRoute
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.header.SimpleTopBar
import com.yunuscagliyan.core_ui.components.ripple.NoRippleInteractionSource
import com.yunuscagliyan.core_ui.navigation.CoreScreen
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.movie_detail.viewmodel.video.VideoViewModel


object VideoScreen : CoreScreen<VideoViewModel>() {

    /**
     * The player page is loaded into the web view with this value as its base URL, so it is
     * also the origin the iframe reports to YouTube. It must not be youtube.com - the page
     * would then claim to be YouTube itself and the embed is refused with error 152
     * ("This video is unavailable").
     */
    private const val PLAYER_ORIGIN = "https://cinemaapp.yunuscagliyan.com"

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
        val currentVideoId = videoId ?: EMPTY_STRING
        var loadedVideoId by remember { mutableStateOf<String?>(null) }

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
                    setBackgroundColor(ContextCompat.getColor(context, android.R.color.black))

                    // Initialised by hand so the iframe gets an explicit origin.
                    enableAutomaticInitialization = false
                    val options = IFramePlayerOptions.Builder()
                        .controls(1)
                        .origin(PLAYER_ORIGIN)
                        .build()

                    initialize(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                loadedVideoId = currentVideoId
                                youTubePlayer.loadVideo(currentVideoId, 0f)
                            }

                            override fun onStateChange(
                                youTubePlayer: YouTubePlayer,
                                state: PlayerConstants.PlayerState
                            ) {
                                onStateChange(state == PlayerConstants.PlayerState.PLAYING)
                            }

                            override fun onError(
                                youTubePlayer: YouTubePlayer,
                                error: PlayerConstants.PlayerError
                            ) {
                                Timber.e("YouTube player error: $error (videoId=$currentVideoId)")
                            }
                        },
                        options
                    )
                    enterFullScreen()
                }
            },
            update = { player ->
                // Only reload when the video actually changes, otherwise every
                // recomposition would restart playback from the beginning.
                if (loadedVideoId != currentVideoId) {
                    loadedVideoId = currentVideoId
                    player.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(currentVideoId, 0f)
                        }
                    })
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
            // The player draws edge to edge, so the bar keeps clear of the status bar.
            modifier = Modifier.statusBarsPadding(),
            title = videoName ?: EMPTY_STRING,
            backgroundColor = CinemaAppTheme.colors.blackColor.copy(
                alpha = 0.6f
            ),
            rightActions = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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