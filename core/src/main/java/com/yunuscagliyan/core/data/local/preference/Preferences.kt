package com.yunuscagliyan.core.data.local.preference

interface Preferences {
    var shouldShowOnBoard: Boolean

    companion object{
        const val KEY_SHOULD_SHOW_ON_BOARDING="should_show_on_boarding"
    }
}