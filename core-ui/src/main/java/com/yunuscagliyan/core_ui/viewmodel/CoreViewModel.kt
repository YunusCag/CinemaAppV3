package com.yunuscagliyan.core_ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuscagliyan.core_ui.event.CoreEvent
import com.yunuscagliyan.core_ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CoreViewModel @Inject constructor(
) : ViewModel() {
    private val _uiEvent = Channel<CoreEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun sendEvent(event: CoreEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun popBack() {
        sendEvent(
            CoreEvent.Navigation(
                Routes.PopBack
            )
        )
    }
}