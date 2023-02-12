package com.yunuscagliyan.core_ui.event

import androidx.compose.material.SnackbarDuration
import com.yunuscagliyan.core.util.UIText
import com.yunuscagliyan.core_ui.navigation.Routes

sealed class CoreEvent {
    class Navigation(val state: Routes) : CoreEvent()
    data class ShowSnackBar(
        val message: UIText,
        val actionLabel: UIText? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val onClick: (() -> Unit)? = null
    ) : CoreEvent()

    data class Toast(
        val message: UIText
    ) : CoreEvent()
}
