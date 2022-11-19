package com.yunuscagliyan.core_ui.event

import com.yunuscagliyan.core_ui.navigation.Routes

sealed class CoreEvent {
    class Navigation(val state: Routes) : CoreEvent()
}
