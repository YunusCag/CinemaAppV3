package com.yunuscagliyan.movie_detail.viewmodel.video

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : CoreViewModel() {
    val state = mutableStateOf(VideoState())

    init {
        val videoId = savedStateHandle.get<String>(Constants.NavigationArgumentKey.VIDEO_ID_KEY)
        val videoName = savedStateHandle.get<String>(Constants.NavigationArgumentKey.VIDEO_NAME_KEY)
        initState(
            videoId = videoId,
            videoName=videoName
        )
    }

    private fun initState(
        videoId: String?,
        videoName: String?
    ) {
        setState(state) {
            copy(
                videoId = videoId,
                videoName = videoName
            )
        }
    }

    fun changeVideoState(value:Boolean){
        setState(state){
            copy(
                isVideoPlaying = value
            )
        }
    }
}