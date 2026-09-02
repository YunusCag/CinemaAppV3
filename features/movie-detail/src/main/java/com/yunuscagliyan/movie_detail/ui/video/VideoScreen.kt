package com.yunuscagliyan.movie_detail.ui.video

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.delay


object VideoScreen : CoreScreen<VideoViewModel>() {

    /**
     * The player page is loaded into the web view with this value as its base URL, so it is
     * also the origin the iframe reports to YouTube. It must not be youtube.com - the page
     * would then claim to be YouTube itself and the embed is refused with error 152
     * ("This video is unavailable").
     */
    private const val PLAYER_ORIGIN = "https://cinemaapp.yunuscagliyan.com"

    private const val VIDEO_ASPECT_RATIO = 16f / 9f

    /** How long the overlay stays around after a tap while the trailer is playing. */
    private const val CONTROLS_AUTO_HIDE_DELAY_MS = 3_000L

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

        val isLandscape =
            LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

        val onStateChange: (Boolean) -> Unit = remember {
            {
                viewModel.changeVideoState(it)
            }
        }

        // The rest of the app is portrait only, but a trailer is worth turning the phone for:
        // while this screen is up the device orientation decides, and the lock comes back when
        // the screen goes away. The button below stays useful even with auto rotate switched
        // off - an explicit choice keeps the orientation until the user changes it again.
        DisposableEffect(key1 = Unit) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
            onDispose {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                youtubePlayer?.release()
                youtubePlayer = null
            }
        }

        // Landscape is the cinema mode: the video takes the whole screen and the system bars
        // get out of its way until the user swipes them back in.
        ImmersiveMode(enabled = isLandscape)

        var areControlsVisible by remember { mutableStateOf(true) }

        // Paused means the user is looking for the controls; playing means they are in the way.
        LaunchedEffect(state.isVideoPlaying, areControlsVisible) {
            if (!state.isVideoPlaying) {
                areControlsVisible = true
            } else if (areControlsVisible) {
                delay(CONTROLS_AUTO_HIDE_DELAY_MS)
                areControlsVisible = false
            }
        }

        // Like every other video app: back leaves the cinema mode first, then the screen.
        BackHandler(enabled = isLandscape) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CinemaAppTheme.colors.blackColor)
                // Watched on the initial pass so the player still gets the touch it needs for
                // its own controls: any tap brings the overlay back while the video plays.
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            if (event.type == PointerEventType.Press) {
                                areControlsVisible = true
                            }
                        }
                    }
                }
        ) {
            YoutubePlayer(
                // Portrait keeps the trailer at its own 16:9 shape in the middle of the screen
                // instead of stretching the player over the whole page.
                modifier = if (isLandscape) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(VIDEO_ASPECT_RATIO)
                        .align(Alignment.Center)
                },
                videoId = state.videoId,
                onStateChange = onStateChange
            )
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.TopCenter),
                visible = areControlsVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopBar(
                    videoName = state.videoName,
                    isLandscape = isLandscape,
                    onBackPress = {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        viewModel.popBack()
                    },
                    onOrientationChange = {
                        activity.requestedOrientation = if (isLandscape) {
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        } else {
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }
                    }
                )
            }
        }
    }

    /**
     * Hides the status and navigation bars while [enabled], and always hands them back when the
     * screen is left. The activity handles the orientation change itself, so this follows the
     * rotation without the player being torn down and the trailer restarting.
     */
    @Composable
    private fun ImmersiveMode(enabled: Boolean) {
        val view = LocalView.current
        val window = (view.context as Activity).window
        val controller = remember(view, window) { WindowCompat.getInsetsController(window, view) }

        // Hiding and showing the bars resets the icon colours the app theme picked, which
        // would leave dark icons on the dark top bar of the page behind this one, so the
        // colours are remembered here and handed back every time the bars change.
        val isAppearanceLightStatusBars = remember { controller.isAppearanceLightStatusBars }
        val isAppearanceLightNavigationBars =
            remember { controller.isAppearanceLightNavigationBars }

        val restoreBarAppearance = {
            controller.isAppearanceLightStatusBars = isAppearanceLightStatusBars
            controller.isAppearanceLightNavigationBars = isAppearanceLightNavigationBars
        }

        DisposableEffect(key1 = enabled) {
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            if (enabled) {
                controller.hide(WindowInsetsCompat.Type.systemBars())
            } else {
                controller.show(WindowInsetsCompat.Type.systemBars())
            }
            restoreBarAppearance()
            onDispose {
                controller.show(WindowInsetsCompat.Type.systemBars())
                restoreBarAppearance()
            }
        }
    }

    @Composable
    private fun YoutubePlayer(
        modifier: Modifier = Modifier,
        videoId: String?,
        onStateChange: (Boolean) -> Unit
    ) {
        val currentVideoId = videoId ?: EMPTY_STRING
        var loadedVideoId by remember { mutableStateOf<String?>(null) }

        AndroidView(
            modifier = modifier.background(color = CinemaAppTheme.colors.blackColor),
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
        isLandscape: Boolean,
        onBackPress: () -> Unit,
        onOrientationChange: () -> Unit
    ) {
        // The scrim spans the whole width while its content keeps clear of the status bar and
        // of the camera cutout that sits on the side once the phone is turned - padding the
        // bar itself would leave an uncovered strip next to the cutout.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    CinemaAppTheme.colors.blackColor.copy(
                        alpha = 0.6f
                    )
                )
        ) {
            SimpleTopBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .displayCutoutPadding(),
                title = videoName ?: EMPTY_STRING,
                backgroundColor = Color.Transparent,
                rightActions = {
                    IconButton(
                        onClick = onOrientationChange,
                        interactionSource = NoRippleInteractionSource()
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isLandscape) {
                                    R.drawable.ic_fullscreen_exit
                                } else {
                                    R.drawable.ic_fullscreen
                                }
                            ),
                            contentDescription = stringResource(
                                id = if (isLandscape) {
                                    R.string.video_portrait_button_description
                                } else {
                                    R.string.video_landscape_button_description
                                }
                            ),
                            modifier = Modifier
                                .size(24.dp),
                            tint = CinemaAppTheme.colors.whiteColor
                        )
                    }
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
}
