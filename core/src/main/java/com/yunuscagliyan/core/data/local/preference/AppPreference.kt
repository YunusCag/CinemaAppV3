package com.yunuscagliyan.core.data.local.preference

import android.content.SharedPreferences

class AppPreference(
    private val sharedPref: SharedPreferences
) : Preferences {
    override var shouldShowOnBoard: Boolean
        get() = sharedPref.getBoolean(Preferences.KEY_SHOULD_SHOW_ON_BOARDING, true)
        set(value) {
            sharedPref.edit()
                .putBoolean(Preferences.KEY_SHOULD_SHOW_ON_BOARDING, value)
                .apply()
        }
}